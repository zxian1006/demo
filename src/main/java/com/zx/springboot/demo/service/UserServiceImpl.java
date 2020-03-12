package com.zx.springboot.demo.service;

import com.zx.springboot.demo.entity.User;
import com.zx.springboot.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public List<User> getUser() throws Exception {
        return mapper.getUser();
    }

    @Override
    public void deleteUser(int id) throws Exception {
        mapper.deleteUser(id);
    }

    @Override
    public void addUser(User user) throws Exception {
        mapper.addUser(user);
    }
}
