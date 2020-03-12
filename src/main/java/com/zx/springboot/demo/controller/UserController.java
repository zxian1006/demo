package com.zx.springboot.demo.controller;

import com.zx.springboot.demo.entity.User;
import com.zx.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private User user;

    //显示用户
    @RequestMapping("list")
    public List<User> index() throws Exception {
        return userService.getUser();
    }
    //删除用户
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable int id) throws Exception {
        userService.deleteUser(id);
        return "你已经删掉了id为"+id+"的用户";
    }
    //增加用户
    @RequestMapping("addUser")
    @Async
    public String addUser() throws Exception {
        user.setAge(33);
        user.setName("阿花");
        userService.addUser(user);

        return "增加用户";
    }
}
