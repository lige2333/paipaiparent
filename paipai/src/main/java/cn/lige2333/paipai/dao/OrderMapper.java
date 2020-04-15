package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.Bid;
import cn.lige2333.paipai.entity.Order;
import cn.lige2333.paipai.entity.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OrderMapper extends Mapper<Order> {
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into `order`(id,product_id,buyer_id,seller_name,price,address_id,state) values (#{order.id},#{order.product.id},#{order.buyerId},#{order.sellerName},#{order.price},-1,#{order.state})")
    int insert(@Param("order") Order order);

    List<Order> selectByBuyerId(Integer buyerId);

    @Update("update `order` set state=#{state},address_id=#{addressId} where id=#{orderId}")
    void updateOrderState(Integer state,Integer addressId,String orderId);

    @Update("update `order` set state=#{state} where id=#{orderId}")
    void updateOrderStates(Integer state,String orderId);


    List<Order> selectBySellerName(String sellerName);

    Order selectByOrderId(String id);
}