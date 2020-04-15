package cn.lige2333.paipai.entity;

import com.auth0.jwt.internal.org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

public class Product implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String name;

    @Column(name = "start_price")
    private BigDecimal startPrice;

    @Column(name = "min_add_price")
    private BigDecimal minAddPrice;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "seller_id")
    private Integer sellerId;

    private String image;

    private Integer state;

    private Integer buyerCount;

    private String category;

    private String district;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getBuyerCount() {
        return buyerCount==null?0:buyerCount;
    }

    public void setBuyerCount(Integer buyerCount) {
        this.buyerCount = buyerCount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startPrice=" + startPrice +
                ", minAddPrice=" + minAddPrice +
                ", currentPrice=" + currentPrice +
                ", sellerId=" + sellerId +
                ", image='" + image + '\'' +
                ", state='" + state + '\'' +
                ", details='" + details + '\'' +
                ", startDate=" + startDate +
                ", days=" + days +
                ", bids=" + bids +
                '}';
    }

    private String details;

    @Column(name = "start_date")
    private Date startDate;

    private String expireDate;

    private String status;

    private Integer days;

    private List<Bid> bids;

    private String startDateString;

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    private static final long serialVersionUID = 1L;

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }



    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return start_price
     */
    public BigDecimal getStartPrice() {
        return startPrice;
    }

    /**
     * @param startPrice
     */
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * @return min_add_price
     */
    public BigDecimal getMinAddPrice() {
        return minAddPrice;
    }

    /**
     * @param minAddPrice
     */
    public void setMinAddPrice(BigDecimal minAddPrice) {
        this.minAddPrice = minAddPrice;
    }

    /**
     * @return current_price
     */
    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice
     */
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * @return seller_id
     */
    public Integer getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
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

    /**
     * @return details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return days
     */
    public Integer getDays() {
        return days;
    }

    /**
     * @param days
     */
    public void setDays(Integer days) {
        this.days = days;
    }
    public String getExpireDate(){
        Date date = DateUtils.addSeconds(getStartDate(), days * 24 * 60 * 60);
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
    }
    public String getStartDateString(){
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(getStartDate());
    }
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        if(state==0){
            return "竞拍中！";
        }else if(state==1){
            return "竞拍完成！";
        }else if(state==2){
            return "无人竞价，流拍！";
        }else if(state==3){
            return "尚未开始竞拍！";
        }else if(state==4){
            return "已被禁用！";
        }else if(state==5){
            return "待审核！";
        }else if(state==6){
            return "审核失败！";
        }else{
            return "未知状态！";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}