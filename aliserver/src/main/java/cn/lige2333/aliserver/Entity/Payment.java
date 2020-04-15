package cn.lige2333.aliserver.Entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Payment implements Serializable {
    private String id;
    private String outTradeNo;
    private String tradeNo;
    private String tradeStatus;
    private Integer status;
}
