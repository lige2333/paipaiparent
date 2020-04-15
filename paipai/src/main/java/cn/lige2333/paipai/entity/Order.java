package cn.lige2333.paipai.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

public class Order implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private String id;

    private Product product;

    @Column(name = "buyer_id")
    private Integer buyerId;

    @Column(name = "seller_name")
    private String sellerName;

    private BigDecimal price;

    private Address address;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public String getStatus() {
        if(state==0){
            return "待完善地址信息！";
        }else if(state==1){
            return "待发货！";
        }else if(state==2){
            return "待收货！";
        }else if(state==3){
            return "已完成！";
        }else if(state==4){
            return "待支付！";
        }else{
            return "未知状态！";
        }
    }
}