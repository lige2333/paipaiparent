package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.entity.*;
import cn.lige2333.paipai.server.WebSocketServer;
import cn.lige2333.paipai.service.*;
import cn.lige2333.paipai.utils.BaseController;
import cn.lige2333.paipai.utils.UserHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController extends BaseController {
	private final String PREFIX = "demo/";
	@Autowired
	ProductService productService;
	@Autowired
	BidService bidService;
	@Autowired
	OrderService orderService;
	@Autowired
	AddressService addressService;
	@Autowired
	MessageService messageService;
	@Autowired
	CollectionService collectionService;
	@Autowired
	CategoryService categoryService;
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("products",productService.getIndexProducts());
		return "shop/index";
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
		if("history".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Bid> history = bidService.getBidHistory(currentUser.getId());
			model.addAttribute("history",history);
		}
		if("upload".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Category> categories = categoryService.findAll();
			model.addAttribute("category",categories);
		}
		if("order".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Order> order = orderService.selectByBuyerId(currentUser.getId());
			model.addAttribute("order",order);
		}
		if("proDetails".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Product> product =productService.selectProductsBySellerId(currentUser.getId());
			model.addAttribute("product",product);
		}
		if("sellerOrder".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Order> product =orderService.selectBySellerName(currentUser.getUsername());
			model.addAttribute("order",product);
		}
		if("message".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			String s = messageService.queryAll(currentUser.getId());
			System.out.println(s);
			ObjectMapper mapper = new ObjectMapper();
			List<Message> messages = mapper.readValue(s, new TypeReference<List<Message>>() {
			});
			model.addAttribute("messages",messages);
		}
		if("collections".equals(work)){
			User currentUser = (User) UserHelper.getCurrentPrincipal();
			List<Collection> collections = collectionService.getCollectionByUser(currentUser.getId());
			List<Product> product = new ArrayList<>();
			for (Collection collection : collections) {
				Product productsById = productService.getProductsById(collection.getProid());
				product.add(productsById);
			}
			model.addAttribute("product",product);
		}
		return "center/"+work;
	}

	@RequestMapping({"/success","/info"})
	public String success() {
		return "demo/success";
	}
	@GetMapping("/userlogin")
	public String loginPage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth instanceof AnonymousAuthenticationToken){
			return PREFIX+"login";
		}else{
			return "shop/index";
		}

	}
    @GetMapping("/register")
    public String register() {
	    return PREFIX+"register";

    }
	@GetMapping("/chat")
	public String chat(HttpServletResponse response,Model model) {
		User currentPrincipal = (User) UserHelper.getCurrentPrincipal();
		setCookieVal(response,"currUser",currentPrincipal.getUsername());
		return PREFIX+"chat";

	}
	@GetMapping("/updateUser")
	public String updateUser(Model model) {
		model.addAttribute("user",(User)UserHelper.getCurrentPrincipal());
		return PREFIX+"update";
	}
	@GetMapping("/sendMessage")
	public String sendMessage(Model model) {
		model.addAttribute("user",(User)UserHelper.getCurrentPrincipal());
		return PREFIX+"sendMessage";
	}
	@GetMapping("/listBidder")
	public String listBidder(Model model,Integer proId) {
		List<Bid> history = bidService.getBiddersByProId(proId);
		model.addAttribute("history",history);
		return "center/history";
	}

	@GetMapping("/updateAddress")
	public String myAddress(@ModelAttribute("orderId") String orderId) {

		return PREFIX+"address";
	}
	@GetMapping("/list")
	public String list(@ModelAttribute("keyword") String keyword,@ModelAttribute("state") String state,@ModelAttribute("district") String district,@ModelAttribute("categorys") String category,Model model) {
		List<Category> categories = categoryService.findAll();
		model.addAttribute("category",categories);
		return "shop/lists";
	}
	@GetMapping("/payment")
	public String payment() {
		return "shop/pay";
	}
	@GetMapping("/detail")
	public String details(Model model) {
		Object prod = model.getAttribute("prod");
		return "shop/details";
	}
}