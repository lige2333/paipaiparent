package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PaymentMapper extends Mapper<Payment> {
    @Select("update payment set payment_status=#{status} where payment_id=#{id}")
    void updateStatus(@Param("id") String id, @Param("status") Integer status);
}
