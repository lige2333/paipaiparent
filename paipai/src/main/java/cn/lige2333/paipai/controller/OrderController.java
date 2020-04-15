package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.Order;
import cn.lige2333.paipai.service.OrderService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;
	@ResponseBody
	@PostMapping("/deliverGoods")
	public String deliverGoods(String orderId){
		try {
			orderService.changeStatusById(orderId);
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
		return "success";
	}
}