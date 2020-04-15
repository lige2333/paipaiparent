package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.UserMapper;
import cn.lige2333.paipai.entity.*;
import cn.lige2333.paipai.entity.Collection;
import cn.lige2333.paipai.utils.SystemMessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.lige2333.paipai.dao.OrderMapper;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ProductService productService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private MessageService messageService;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	RabbitTemplate rabbitTemplate;

	public OrderMapper getOrderMapper() {
		return orderMapper;
	}

	public Order createOrder(Bid bid){
		Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setProduct(bid.getProduct());
		order.setBuyerId(bid.getBuyer().getId());
		order.setSellerName(userMapper.loadUsernameById(bid.getProduct().getSellerId()));
		order.setState(0);
		order.setPrice(bid.getBidPrice());
		orderMapper.insert(order);
		return order;
	}

	public List<Order> selectByBuyerId(Integer id) {
		return orderMapper.selectByBuyerId(id);
	}

    public List<Order> selectBySellerName(String name) {
		return orderMapper.selectBySellerName(name);
    }

	public void changeStatusById(String orderId) {
		orderMapper.updateOrderStates(2,orderId);
	}

	@Scheduled(cron = "0/1 * * * * *")
	public void createOrder() throws JsonProcessingException {
		String date=new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date());
		List<Product> products = productService.putProducts();
		for (Product product : products) {
			if(product.getState()==0&&date.equals(product.getExpireDate())){
				redisTemplate.opsForZSet().remove("rank", product.getId());
				productService.createOrd(product);
			}
		}


	}
	@Scheduled(cron = "0/1 * * * * *")
	public void startSale() throws JsonProcessingException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String date=sdf.format(new Date());
		String afterHourString =sdf.format(DateUtils.addMinutes(new Date(), 60)) ;
		List<Product> products = productService.putProducts();
		for (Product product : products) {
			if(product.getState()==3&&date.equals(product.getStartDateString())){
				redisTemplate.opsForZSet().add("rank", product.getId(),0);
				productService.updateProduct(product);
			}else if(product.getState()==3&&afterHourString.equals(product.getStartDateString())){
				List<Collection> collectionByProductId = collectionService.getCollectionByProductId(product.getId());
				for (Collection collection : collectionByProductId) {
					String username = userMapper.loadUsernameById(collection.getUserid());
					SystemMessageUtil.sendToOne("您收藏的商品"+product.getName()+"即将在60分钟后开售，请及时参与竞拍！", collection.getUserid(), username, messageService);
				}

			}else if(product.getState()==5&&date.equals(product.getStartDateString())){
				String user = userMapper.loadUsernameById(product.getSellerId());
				SystemMessageUtil.sendToOne("很遗憾！商品"+product.getName()+"审核失败，请重新上传！",product.getSellerId(),user,messageService);
				productService.auditFailed(product);
			}
		}

	}

	public Order selectByOrderId(String orderId) {
		Order order = new Order();
		order.setId(orderId);
		return orderMapper.selectByOrderId(orderId);
	}

	@RabbitListener(queues = "exchange-ttl-to.queue")
	public void receive(Map<String, Object> map) throws JsonProcessingException {
		String orderId = (String)map.get("orderId");
		String productName = (String)map.get("productName");
		Integer buyerId = (Integer)map.get("buyerId");
		String buyerName = (String)map.get("buyerName");
		SystemMessageUtil.sendToOne("恭喜，商品"+productName+"已经生成订单！订单号为"+orderId+"，请尽快完善地址信息并支付！", buyerId,buyerName, messageService);
	}
}