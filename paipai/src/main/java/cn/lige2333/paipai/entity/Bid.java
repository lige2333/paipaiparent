package cn.lige2333.paipai.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;

public class Bid implements Serializable {
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private Product product;

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", proId=" + product +
                ", bidPrice=" + bidPrice +
                ", buyer=" + buyer +
                ", time=" + time +
                ", state=" + state +
                '}';
    }

    @Column(name = "bid_price")
    private BigDecimal bidPrice;

    private User buyer;

    private Date time;

    private Integer state;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return bid_price
     */
    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    /**
     * @param bidPrice
     */
    public void setBidPrice(BigDecimal bidPrice) {
        this.bidPrice = bidPrice;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getTime() {
        return time;
    }

    public String getFormTime() {

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }
    public String getStatus() {
        if(state==0){
            return "等待竞拍结果！";
        }else if(state==1){
            return "竞价成功！";
        }else if(state==2){
            return "竞价失败！";
        }else{
            return "未知状态！";
        }
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}