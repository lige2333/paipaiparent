package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.entity.Collection;
import cn.lige2333.paipai.utils.BaseController;
import cn.lige2333.paipai.utils.CodeUtils;
import cn.lige2333.paipai.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.Code;
import cn.lige2333.paipai.service.CodeService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {
	@Autowired
	private CodeService codeService;

	@RequestMapping("/getCode")
	@ResponseBody
	public String getCode(@RequestParam("telephone") String telephone){
		if(!VerifyUtils.isMobile(telephone)){
			return "发送失败！手机号不合法！";
		}
		String randomCode = CodeUtils.randomCode();
		try {
			CodeUtils.sendMessage(telephone, randomCode);
			codeService.saveCode(randomCode,telephone);
			return "success";
		}catch (RuntimeException e){
			e.printStackTrace();
			return e.getMessage();
		}
	}

}