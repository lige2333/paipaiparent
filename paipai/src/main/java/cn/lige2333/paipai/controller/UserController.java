package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.server.WebSocketServer;
import cn.lige2333.paipai.service.CodeService;
import cn.lige2333.paipai.utils.BaseController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.service.UserService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	@Autowired
	private CodeService codeService;
	@RequestMapping("/userReg")
	@ResponseBody
	public String userReg(User user,String code){
		try {
			codeService.checkCode(user,code);
			userService.insertUser(user);
			return "success";
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
	}
	@RequestMapping("/checkUsername")
	@ResponseBody
	public Boolean checkUsername(String username){
		return !userService.checkUsername(username);
	}
	@RequestMapping("/userInfoUp")
	@ResponseBody
	public String userInfoUp(User user,String code){
		try {
			codeService.checkCode(user,code);
			userService.updateUser(user);
			return "success";
		}catch (Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@RequestMapping("/getAllUser")
	@ResponseBody
	public String getAllUser() throws JsonProcessingException {
		List<User> all = userService.getAll();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		String s = mapper.writerFor(new TypeReference<List<User>>() {
		}).writeValueAsString(all);
		return s;
	}
	@RequestMapping("/setState")
	@ResponseBody
	public String setState(Integer id,Integer status) {
		try {
			userService.setStatus(id,status);
			return "success";
		}catch (Exception e){
			return e.getMessage();
		}

	}

	@RequestMapping("/getOnlineUsers")
	@ResponseBody
	public Map<String, Object> getOnlineUsers() {
		try {
			List<String> currentUsers = WebSocketServer.getCurrentUsers();
			Map<String, Object> map = new HashMap<>();
			map.put("userlist", currentUsers);
			return map;
		}catch (Exception e){
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "failed");
			return map;
		}

	}

}