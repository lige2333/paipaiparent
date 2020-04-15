package cn.lige2333.paipai.controller;

import cn.lige2333.paipai.entity.Order;
import cn.lige2333.paipai.entity.Payment;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.entity.User;
import cn.lige2333.paipai.service.OrderService;
import cn.lige2333.paipai.service.PaymentService;
import cn.lige2333.paipai.service.ProductService;
import cn.lige2333.paipai.utils.UserHelper;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class PaymentController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PaymentService paymentService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @RequestMapping("/insuranceProcess")
    @ResponseBody
    public String insuranceProcess(Integer proId,String amount){
        try {
            Payment payment = new Payment();
            User current = (User) UserHelper.getCurrentPrincipal();
            payment.setProductId(proId);
            payment.setPaymentStatus(1);
            payment.setUserId(current.getId());
            Product productsById = productService.getProductsById(proId);
            if(productsById.getSellerId()==current.getId()){
                Map<String,Object> map = new HashMap<>();
                map.put("msg", "failed");
                map.put("msg2", "不能为自己拍卖的商品支付保证金！");
                String pay = JSON.toJSONString(map);
                return pay;
            }
            if(paymentService.isPaidInsurance(payment)){
                Map<String,Object> map = new HashMap<>();
                map.put("msg", "failed");
                map.put("msg2", "保证金已经支付！");
                String pay = JSON.toJSONString(map);
                return pay;
            }
            String payId = UUID.randomUUID().toString();
            payment.setPaymentId(payId);
            payment.setPaymentStatus(0);
            payment.setAmount(new BigDecimal(amount));
            payment = paymentService.insert(payment);
            String pay = JSON.toJSONString(payment);
            return pay;
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("msg", "failed");
            String pay = JSON.toJSONString(map);
            return pay;
        }


    }

    @RequestMapping("/productPaymentProcess")
    @ResponseBody
    public String productPaymentProcess(String orderId){
        try {
            Order order = orderService.selectByOrderId(orderId);
            Payment payment = new Payment();
            User current = (User) UserHelper.getCurrentPrincipal();
            payment.setProductId(order.getProduct().getId());
            payment.setPaymentStatus(4);
            payment.setUserId(current.getId());
            if(paymentService.isPaidInsurance(payment)){
                Map<String,Object> map = new HashMap<>();
                map.put("msg", "failed");
                map.put("msg2", "订单已经支付！");
                String pay = JSON.toJSONString(map);
                return pay;
            }
            payment.setPaymentId(orderId);
            payment.setPaymentStatus(2);
            Payment insurancePayments = paymentService.getInsurancePayments(order.getProduct().getId(), current.getId());
            BigDecimal paid = insurancePayments.getAmount();
            BigDecimal remain = order.getPrice().subtract(paid);
            payment.setAmount(remain);
            payment = paymentService.insert(payment);
            String pay = JSON.toJSONString(payment);
            return pay;
        }catch (Exception e){
            e.printStackTrace();
            Map<String,Object> map = new HashMap<>();
            map.put("msg", "failed");
            String pay = JSON.toJSONString(map);
            return pay;
        }


    }

}
