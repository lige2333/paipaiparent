package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.utils.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.Address;
import cn.lige2333.paipai.service.AddressService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddressController extends BaseController {
	@Autowired
	private AddressService addressService;

	@RequestMapping("/addressUp")
	@ResponseBody
	public String addressUp(Address address){
		try {
			addressService.updateAddress(address);
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
		return "success";
	}

}