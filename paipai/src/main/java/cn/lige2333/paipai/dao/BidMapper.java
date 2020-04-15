package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.Bid;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface BidMapper extends Mapper<Bid> {
    List<Bid> selectByProId(Integer proId);

    @Select("select max(bid_price) from bid where pro_id=#{proid} group by pro_id")
    BigDecimal selectMaxBidPriceByProId(Integer proid);

    @Insert("insert into bid(pro_id,bid_price,buyer_id,time,state) values (#{bid.product.id},#{bid.bidPrice},#{bid.buyer.id},#{bid.time},#{bid.state})")
    void insertBid(@Param("bid") Bid bid);

    @Select("select count(distinct buyer_id) from bid where pro_id=#{id} group by pro_id")
    Integer selectCountByProId(Integer id);

    @Select("select count(*) from bid where pro_id=#{id}")
    Integer selectBidCountByProId(Integer id);

    List<Bid> getBidHistory(Integer id);

    Bid selectMaxPriceBid(Integer id);

    void updateFailedBidState(Integer id);

    void updateSuccessBidState(Integer id);

}