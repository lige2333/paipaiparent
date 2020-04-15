package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.entity.Payment;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.service.PaymentService;
import cn.lige2333.paipai.service.ProductService;
import cn.lige2333.paipai.utils.BaseController;
import cn.lige2333.paipai.utils.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.Bid;
import cn.lige2333.paipai.service.BidService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BidController extends BaseController {
	@Autowired
	private BidService bidService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentService paymentService;

	@RequestMapping("/biddingForProd")
	private ModelAndView biddingForProd(Integer proId){
		Product p=productService.getProductsById(proId);
		Payment payment = new Payment();
		User current = (User) UserHelper.getCurrentPrincipal();
		payment.setProductId(proId);
		payment.setPaymentStatus(1);
		payment.setUserId(current.getId());
		boolean paidInsurance = paymentService.isPaidInsurance(payment);
		ModelAndView mv = new ModelAndView();
		mv.addObject("prod", p);
		mv.addObject("payInsurance", paidInsurance);
		mv.setViewName("shop/bidding");
		return mv;
	}
	@ResponseBody
	@RequestMapping("/addBid")
	private String addBid(Integer proState,Integer proId, BigDecimal price,Integer sellerId){
		User user = (User) UserHelper.getCurrentPrincipal();
		Payment payment = new Payment();
		payment.setProductId(proId);
		payment.setPaymentStatus(1);
		payment.setUserId(user.getId());
		if(sellerId.equals(user.getId())){
			return "您不能参与自己发布的商品的竞价！";
		}
		if(!proState.equals(0)){
			return "竞拍已经结束！";
		}
		if(!paymentService.isPaidInsurance(payment)){
			return "请先缴纳保证金！";
		}
		Bid bid = new Bid();
		bid.setBidPrice(price);
		bid.setBuyer(user);
		bid.setTime(new Date());
		Product product = new Product();
		product.setId(proId);
		bid.setProduct(product);
		bid.setState(0);
		try {
			bidService.createBid(bid);
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
		return "success";
	}
	@ResponseBody
	@RequestMapping("/getBidsById")
	public String getBidsById(Integer proId) throws JsonProcessingException {
		List<Bid> all = bidService.getBiddersByProId(proId);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String s = mapper.writerFor(new TypeReference<List<Bid>>() {
		}).writeValueAsString(all);
		return s;
	}
}