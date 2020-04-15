package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.Code;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface CodeMapper extends Mapper<Code> {
    @Select("select * from code where telephone=#{telephone} order by expire_date desc limit 1")
    Code selectByTelephoneAndDate(String telephone);
}