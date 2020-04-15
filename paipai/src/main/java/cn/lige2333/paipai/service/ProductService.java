package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.BidMapper;
import cn.lige2333.paipai.dao.UserMapper;
import cn.lige2333.paipai.entity.Bid;
import cn.lige2333.paipai.entity.Order;
import cn.lige2333.paipai.entity.Payment;
import cn.lige2333.paipai.model.Log;
import cn.lige2333.paipai.query.ProductQueryObject;
import cn.lige2333.paipai.server.WebSocketServer;
import cn.lige2333.paipai.utils.MyPage;
import cn.lige2333.paipai.utils.SystemMessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.dao.ProductMapper;

import java.io.IOException;
import java.util.*;

@Transactional
@Service
public class ProductService {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private BidMapper bidMapper;
	@Autowired
	private OrderService orderService;
	@Autowired
	RedisTemplate<String, Object> proRedisTemplate;
	@Autowired
	MessageService messageService;
	@Autowired
	PaymentService paymentService;
	@Autowired
	UserMapper userMapper;
	@Autowired
	RabbitTemplate rabbitTemplate;
	public ProductMapper getProductMapper() {
		return productMapper;
	}

    public List<Product> getIndexProducts() {
		Set<Object> rank = proRedisTemplate.opsForZSet().reverseRange("rank", 0, 4);
		ArrayList<Product> products = new ArrayList<>();
		for (Object o : rank) {
			Product product = productMapper.selectById((Integer) o);
			products.add(product);
		}
		return products;
	}

	@Log(module = "前台",description = "搜索")
	public MyPage<Product> getProductsByConditions(ProductQueryObject qo) {
		if(qo.getKeyword()!=null&&!"".equals(qo.getKeyword())){
			JiebaSegmenter js = new JiebaSegmenter();
			List<SegToken> process = js.process(qo.getKeyword(), JiebaSegmenter.SegMode.SEARCH);
			Set<Object> members = new HashSet<>();
			for (SegToken segToken : process) {
				System.out.println(segToken.word);
				if(segToken.word.length()>1){
					members.addAll(proRedisTemplate.opsForSet().members(segToken.word));
				}
			}
			qo.setKeys(members);
		}
		MyPage<Product> productMyPage = MyPage.selectKeywordsByPage(productMapper, qo);
		return productMyPage;
	}

    public Product getProductsById(Integer id) {
		Product product = productMapper.selectById(id);
		product.setBuyerCount(bidMapper.selectCountByProId(product.getId()));
		return product;
    }
	public List<Product> getProductsByState(Integer state) {
		List<Product> products = productMapper.selectByState(state);
		return products;
	}
	@CacheEvict(cacheNames = "pros",allEntries = true)
    public void updateProduct(Product product) {
		productMapper.updateState(product.getId(), 0);
		try {
			WebSocketServer.BroadCastInfo("start");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	@CacheEvict(cacheNames = "pros",allEntries = true)
	public void auditFailed(Product product) {
		productMapper.updateState(product.getId(), 6);
	}
	@CacheEvict(cacheNames = "pros",allEntries = true)
	public Product insertProduct(Product product) {
		Integer proid = productMapper.insert(product);

		return product;
	}
	@CacheEvict(cacheNames = "pros",allEntries = true)
	public void createOrd(Product product) throws JsonProcessingException {
		if(bidMapper.selectBidCountByProId(product.getId())==0){
			productMapper.updateState(product.getId(),2);
			try {
				WebSocketServer.BroadCastInfo("nobody");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			productMapper.updateState(product.getId(), 1);
			bidMapper.updateFailedBidState(product.getId());
			bidMapper.updateSuccessBidState(product.getId());
			Bid maxPriceBid = bidMapper.selectMaxPriceBid(product.getId());
			List<Payment> payments = paymentService.getPayments(product.getId());
			for (Payment payment:payments) {
				if(payment.getUserId()!=maxPriceBid.getBuyer().getId()){
					paymentService.refund(product.getId(),payment.getUserId());
				}
			}
			Order order = orderService.createOrder(maxPriceBid);
			try {
				SystemMessageUtil.sendToOne("恭喜您！商品"+product.getName()+"竞拍成功！请及时进行地址完善与支付。",maxPriceBid.getBuyer().getId(),maxPriceBid.getBuyer().getUsername(),messageService);
				WebSocketServer.BroadCastInfo("end");
				Map<String, Object> map = new HashMap<>();
				map.put("orderId",order.getId());
				map.put("productName",product.getName());
				map.put("buyerId",maxPriceBid.getBuyer().getId());
				map.put("buyerName",maxPriceBid.getBuyer().getUsername());
				rabbitTemplate.convertAndSend("exchange-ttl-from", "", map);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Cacheable(cacheNames = "pros",key = "1")
	public List<Product> putProducts(){
		List<Product> products = productMapper.selectAllBySmall();
		return products;
	}



    public List<Product> selectProductsBySellerId(Integer id) {
		return productMapper.selectBySellerId(id);
    }

	public void addIndex() {
		JiebaSegmenter js = new JiebaSegmenter();
		List<Product> products = productMapper.selectAll();
		for (Product product : products) {
			List<SegToken> process = js.process(product.getName(), JiebaSegmenter.SegMode.SEARCH);
			for (SegToken segToken : process) {
				if(segToken.word.length()>1){
					proRedisTemplate.opsForSet().add(segToken.word,product.getId());
				}
			}
		}

	}

    public List<Product> getAllPros() {
    	return productMapper.selectAll();
	}

	@CacheEvict(cacheNames = "pros",allEntries = true)
	public void changeStateById(Integer id, Integer state) {
		Product product = productMapper.selectById(id);
		productMapper.updateState(id, state);
		String user = userMapper.loadUsernameById(product.getSellerId());
		if(state==3){
			try {
				SystemMessageUtil.sendToOne("恭喜您！商品"+product.getName()+"审核通过！请耐心等待拍卖开始！",product.getSellerId(),user,messageService);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}else if(state==6){
				try {
					SystemMessageUtil.sendToOne("很遗憾！商品"+product.getName()+"审核失败，请重新上传！",product.getSellerId(),user,messageService);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		}
	}
}