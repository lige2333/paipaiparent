package cn.lige2333.paipai.dao;

import cn.lige2333.paipai.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    @Select("select * from user where username=#{username}")
    User loadUserByUsername(@Param("username") String s);

    @Select("select id,username,0 as password,telephone,status from user")
    List<User> getAll();

    @Select("select username from user where id=#{id}")
    String loadUsernameById(Integer id);

    @Insert("Insert into user(username,password,telephone) values (#{username},#{realPassword},#{telephone})")
    void insertUser(User user);

    @Update("Update user set password=#{realPassword},telephone=#{telephone} where id=#{id}")
    void updateUser(User user);
    @Update("Update user set status=#{status} where id=#{id}")
    void setStatus(Integer id,Integer status);
}