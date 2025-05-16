package com.example.heartdiagnosis.mapper;

import com.example.heartdiagnosis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User login(@Param("username") String username, @Param("password") String password);
    
    @Insert("INSERT INTO user(username, password, role) VALUES(#{username}, #{password}, #{role})")
    int register(User user);
    
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
} 