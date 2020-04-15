package cn.lige2333.paipai.service;

import cn.lige2333.paipai.dao.OrderMapper;
import cn.lige2333.paipai.dao.PaymentMapper;
import cn.lige2333.paipai.dao.ProductMapper;
import cn.lige2333.paipai.dao.UserMapper;
import cn.lige2333.paipai.entity.Payment;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.utils.SystemMessageUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    PaymentMapper paymentMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserMapper userMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    public Payment insert(Payment payment) {
        paymentMapper.insert(payment);
        return payment;
    }

    public boolean isPaidInsurance(Payment payment) {
        return paymentMapper.selectCount(payment)>0;
    }

    @RabbitListener(queues = "lige2333")
    public void receive(Map<String, Object> map) {
        String outTradeNo = (String)map.get("OutTradeNo");
        Payment paymentInsurance = new Payment();
        paymentInsurance.setPaymentId(outTradeNo);
        paymentInsurance.setPaymentStatus(1);
        Payment paymentProduct = new Payment();
        paymentProduct.setPaymentId(outTradeNo);
        paymentProduct.setPaymentStatus(4);

        if(paymentMapper.selectCount(paymentInsurance)>0||paymentMapper.selectCount(paymentProduct)>0){
            return;
        }else {
            Payment pay = null;
            paymentInsurance.setPaymentStatus(0);
            if(paymentMapper.selectOne(paymentInsurance)==null){
                paymentProduct.setPaymentStatus(2);
                pay=paymentMapper.selectOne(paymentProduct);
                pay.setPaymentStatus(4);
                paymentMapper.updateByPrimaryKey(pay);
                orderMapper.updateOrderStates(1, pay.getPaymentId());
                restTemplate.getForObject("http://122.51.138.251:9876/updateStatus?id="+outTradeNo, String.class);
            }else {
                pay=paymentMapper.selectOne(paymentInsurance);
                pay.setPaymentStatus(1);
                paymentMapper.updateByPrimaryKey(pay);
                restTemplate.getForObject("http://122.51.138.251:9876/updateStatus?id="+outTradeNo, String.class);
            }


        }
    }

    public String refund(Integer proId, Integer buyerId) throws JsonProcessingException {
        Payment payment = new Payment();
        payment.setProductId(proId);
        payment.setUserId(buyerId);
        payment.setPaymentStatus(1);
        List<Payment> select = paymentMapper.select(payment);
        if(select==null||select.size()==0){
            return "failed";
        }
        BigDecimal amount = select.get(0).getAmount();
        String paymentId = select.get(0).getPaymentId();
        String state = restTemplate.getForObject("http://122.51.138.251:9876/refund?tradeNo=" + paymentId + "&totalAmount=" + amount, String.class);
        if("success".equals(state)){
            String username = userMapper.loadUsernameById(buyerId);
            Product product = productMapper.selectById(proId);
            SystemMessageUtil.sendToOne("您参与竞拍"+product.getName()+"失败！押金已经退回您的支付宝账户，请注意查收！", buyerId, username, messageService);
            paymentMapper.updateStatus(paymentId, 3);
            return "success";
        }else {
            return "failed";
        }
    }

    public List<Payment> getPayments(Integer proId) {
        Payment payment = new Payment();
        payment.setProductId(proId);
        return paymentMapper.select(payment);
    }

    public Payment getInsurancePayments(Integer proId, Integer userId) {
        Payment payment = new Payment();
        payment.setProductId(proId);
        payment.setUserId(userId);
        payment.setPaymentStatus(1);
        List<Payment> select = paymentMapper.select(payment);
        return select.get(0);
    }
}
