package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.dao.BidMapper;
import cn.lige2333.paipai.entity.Collection;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.query.ProductQueryObject;
import cn.lige2333.paipai.service.BidService;
import cn.lige2333.paipai.service.CollectionService;
import cn.lige2333.paipai.utils.BaseController;
import cn.lige2333.paipai.utils.MyPage;
import cn.lige2333.paipai.utils.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.service.ProductService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class ProductController extends BaseController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private RedisTemplate redisTemplate;
	@Value("${lige2333.filePath}")
	private String filePath;

	@RequestMapping("/findProductByKeyword")
	public ModelAndView home(String keyword,Integer state,String category,String province,String city,String area) {
		String district = province+city+area;
		if("请选择省份请选择城市请选择区县".equals(district)||"nullnullnull".equals(district)){
			district="";
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("keyword", keyword);
		mv.addObject("state", state);
		mv.addObject("categorys", category);
		mv.addObject("district", district);
		mv.setViewName("redirect:/list");
		return mv;
	}
	@ResponseBody
	@RequestMapping("/queryProduct")
	public MyPage<Product> queryProduct(@ModelAttribute("qo")ProductQueryObject qo,String keyword) {
		MyPage<Product> products = productService.getProductsByConditions(qo);
		return products;
	}
	@RequestMapping("/findProductById")
	public String findProductById(Integer id,Model model) {
		Product p=productService.getProductsById(id);
		if(p.getState()==0||p.getState()==3) {
			if (redisTemplate.opsForZSet().rank("rank", p.getId()) == null) {
				redisTemplate.opsForZSet().add("rank", p.getId(), 1);
			} else {
				redisTemplate.opsForZSet().incrementScore("rank", p.getId(), 1);
			}
			int rank = redisTemplate.opsForZSet().score("rank", p.getId()).intValue();
			model.addAttribute("rank",rank);
		}
		if(p.getState()==4){
			throw new RuntimeException("商品已经下架，无法查看！");
		}
		if(p.getState()==5){
			throw new RuntimeException("商品等待审核，无法查看！");
		}
		if(p.getState()==6){
			throw new RuntimeException("商品不存在，无法查看！");
		}

		model.addAttribute("prod",p);

		return "forward:/detail";
	}
	@ResponseBody
	@RequestMapping("/createProduct")
	private HashMap<String,Object> createProduct(Product product, String startDate){
		try {
			product.setStartDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate));
		} catch (ParseException e) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("msg", "日期格式化错误！");
			return map;
		}
		product.setCurrentPrice(product.getStartPrice());
		product.setState(5);
		User current = (User) UserHelper.getCurrentPrincipal();
		product.setSellerId(current.getId());
		Product products = productService.insertProduct(product);
		productService.addIndex();
		HashMap<String, Object> map = new HashMap<>();
		map.put("msg", "success");
		map.put("id", products.getId());
		return map;
	}
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(MultipartFile file) {
		String fileName= UUID.randomUUID().toString()+"-"+file.getOriginalFilename();
		String target = filePath+"\\"+fileName;
		HashMap<String, Object> map = new HashMap<>();
		try {
			file.transferTo(new File(target));
		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "http://127.0.0.1/"+fileName;
	}
	@ResponseBody
	@RequestMapping("/queryAllPros")
	public String queryAllPros() throws JsonProcessingException {
		List<Product> all = productService.getAllPros();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String s = mapper.writerFor(new TypeReference<List<Product>>() {
		}).writeValueAsString(all);
		return s;
	}
	@ResponseBody
	@RequestMapping("/queryProsByState")
	public String queryProsByState(Integer state) throws JsonProcessingException {
		List<Product> all = productService.getProductsByState(state);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String s = mapper.writerFor(new TypeReference<List<Product>>() {
		}).writeValueAsString(all);
		return s;
	}
	@ResponseBody
	@RequestMapping("/changeProState")
	public String changeProState(Integer id,Integer state){
		try {
			productService.changeStateById(id,state);
			productService.addIndex();
			return "success";
		}catch (Exception e){
			return e.getMessage();
		}
	}
	@ResponseBody
	@RequestMapping("/collectPro")
	public String collectPro(Integer proId){
		User user = null;
		try {
			 user = (User) UserHelper.getCurrentPrincipal();
		}catch (Exception e){
			return "请先登录";
		}
		if(collectionService.isExist(user.getId(),proId)){
			return "您已经收藏该商品！";
		}
		Collection collection = new Collection();
		collection.setProid(proId);
		collection.setUserid(user.getId());
		collectionService.collect(collection);
		return "success";
	}
	@ResponseBody
	@RequestMapping("/DelCollectPro")
	public String DelCollectPro(Integer proId){
		User user = null;
		try {
			user = (User) UserHelper.getCurrentPrincipal();
		}catch (Exception e){
			return "请先登录";
		}
		if(!collectionService.isExist(user.getId(),proId)){
			return "收藏不存在！";
		}
		Collection collection = new Collection();
		collection.setProid(proId);
		collection.setUserid(user.getId());
		collectionService.delCollect(collection);
		return "success";
	}
}