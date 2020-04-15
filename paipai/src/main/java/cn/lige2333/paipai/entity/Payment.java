package cn.lige2333.paipai.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String paymentId;
    private Integer userId;
    private Integer paymentStatus;//0：保证金未支付，1：保证金已支付，2：商品未支付,3：保证金退款，4：商品已支付
    private Integer productId;
    private BigDecimal amount;
}
