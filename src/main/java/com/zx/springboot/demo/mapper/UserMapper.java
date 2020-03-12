package com.zx.springboot.demo.mapper;

import com.zx.springboot.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    //获取用户名单
    List<User> getUser() throws Exception;
    //根据id删除用户
    void deleteUser(int id)throws Exception;
    //新增用户
    void addUser(User user)throws Exception;
}
