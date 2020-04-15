package cn.lige2333.aliserver.Dao;

import cn.lige2333.aliserver.Entity.Payment;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PaymentDao extends Mapper<Payment> {
    @Select("update payment set status=#{status} where id=#{id}")
    void updateStatus(String id, Integer status);
}
