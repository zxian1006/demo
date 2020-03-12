package com.zx.springboot.demo.service;

import com.zx.springboot.demo.entity.User;

import java.util.List;

public interface UserService {
    //显示所有用户
     List<User> getUser()throws Exception;
    //根据id删除用户
     void deleteUser(int id)throws Exception;
    //新增用户
     void addUser(User user)throws Exception;
}
