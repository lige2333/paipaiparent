package cn.lige2333.aliserver.service;

import cn.lige2333.aliserver.Dao.PaymentDao;
import cn.lige2333.aliserver.Entity.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    PaymentDao paymentDao;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void addPayment(Payment payment){
        paymentDao.insert(payment);
    }

    @Scheduled(cron = "0/5 * * * * *")
    public void resend(){
        Payment payment = new Payment();
        payment.setStatus(0);
        List<Payment> payments = paymentDao.select(payment);
        if(payments==null||payments.size()==0){
            return;
        }
        for (Payment payment1 : payments) {
            sendMessage(payment1);
        }
    }

    public void sendMessage(Payment payment){
        Map<String, Object> map = new HashMap<>();
        map.put("OutTradeNo",payment.getOutTradeNo());
        map.put("TradeNo",payment.getTradeNo());
        map.put("TradeStatus",payment.getTradeStatus());
        rabbitTemplate.convertAndSend("lige2333", "lige2333",map);
    }
    public void updateStateById(String id,Integer status){
        Payment payment = new Payment();
        payment.setId(id);
        payment.setStatus(status);
        paymentDao.updateStatus(id,status);
    }
}
