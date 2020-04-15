package cn.lige2333.paipaiadmin.controller;

import cn.lige2333.paipaiadmin.Entity.*;
import cn.lige2333.paipaiadmin.service.LogService;
import cn.lige2333.paipaiadmin.service.UsersService;
import cn.lige2333.paipaiadmin.utils.UserHelper;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController{
	private final String PREFIX = "demo/";
	@Autowired
	private UsersService usersService;
	@Autowired
	private LogService logService;
	@RequestMapping("/")
	public String home(Model model) {
		return "redirect:/openWork/info";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work,Model model) {
		if(UserHelper.getCurrentPrincipal() instanceof User){
			model.addAttribute("user",(User)UserHelper.getCurrentPrincipal());
		}
		model.addAttribute("activeUri",work);
		model.addAttribute("work",work);
		return "shop/self_info";
	}
	@RequestMapping("/innerWork/{work}")
	public String innerWork(@PathVariable("work") String work,Model model) throws JsonProcessingException {
		if(UserHelper.getCurrentPrincipal() instanceof User){
			model.addAttribute("user",(User)UserHelper.getCurrentPrincipal());
		}
		model.addAttribute("work",work);
		if("user".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			ObjectMapper mapper = new ObjectMapper();
			List<Users> users = mapper.readValue(usersService.getAllUser(), new TypeReference<List<Users>>() {
			});
			model.addAttribute("users",users);
		}
		if("proDetails".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			ObjectMapper mapper = new ObjectMapper();
			List<Product> products = mapper.readValue(usersService.queryAllPros(), new TypeReference<List<Product>>() {
			});
			model.addAttribute("product",products);
		}
		if("proAudit".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			ObjectMapper mapper = new ObjectMapper();
			List<Product> products = mapper.readValue(usersService.queryProsByState(5), new TypeReference<List<Product>>() {
			});
			model.addAttribute("product",products);
		}
		if("history".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			ObjectMapper mapper = new ObjectMapper();
			List<Logs> logs = mapper.readValue(logService.query(), new TypeReference<List<Logs>>() {
			});
			model.addAttribute("logs",logs);
		}
		return "center/"+work;
	}

	@GetMapping("/userlogin")
	public String loginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
			return PREFIX+"login";
		}else{
			return "redirect:/openWork/info";
		}

	}
	@GetMapping("/listBidder")
	public String listBidder(Model model,Integer proId) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<Bid> history = mapper.readValue(usersService.getBidsById(proId), new TypeReference<List<Bid>>() {
		});
		model.addAttribute("history",history);
		return "center/history";
	}

}