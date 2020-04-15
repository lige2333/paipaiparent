package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.query.ProductQueryObject;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

public interface ProductMapper extends Mapper<Product> {

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into product(name,start_price,min_add_price,current_price,seller_id,image,state,details,start_date,days,district,category) values(#{product.name},#{product.startPrice},#{product.minAddPrice},#{product.currentPrice},#{product.sellerId},#{product.image},#{product.state},#{product.details},#{product.startDate},#{product.days},#{product.district},#{product.category})")
    int insert(@Param("product") Product product);

    int selectCountByConditions(ProductQueryObject qo);

    List<Product> selectByConditions(ProductQueryObject qo);

    @Override
    List<Product> selectAll();

    List<Product> selectAllBySmall();

    Product selectById(Integer id);

    List<Product> selectByState(Integer state);

    @Select("select min_add_price from product where id=#{id}")
    BigDecimal selectMinAddPriceById(Integer id);

    @Update("update product set current_price=#{currentPrice} where id=#{proId}")
    void updateCurentPrice(@Param("currentPrice") BigDecimal bidPrice, @Param("proId") Integer proId);

    @Select("select start_price from product where id=#{id}")
    BigDecimal selectStartPriceById(Integer proId);

    @Update("update product set state=#{state} where id=#{id}")
    void updateState(Integer id,Integer state);

    List<Product> selectBySellerId(Integer id);
}