package cn.lige2333.paipaiadmin.mapper;

import cn.lige2333.paipaiadmin.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    @Select("select * from user where username=#{username}")
    User loadUserByUsername(@Param("username") String s);

    @Select("select username from user where id=#{id}")
    String loadUsernameById(Integer id);

    @Insert("Insert into user(username,password,telephone) values (#{username},#{realPassword},#{telephone})")
    void insertUser(User user);

    @Update("Update user set password=#{realPassword},telephone=#{telephone} where id=#{id}")
    void updateUser(User user);
}