package com.shiro.service.impl;

import com.shiro.domain.User;
import com.shiro.mapper.UserMapper;
import com.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public User findUserByID(Integer id) {
        return userMapper.findUserByID(id);
    }

    @Override
    public void register(String name, String password, String salt) {
        userMapper.insertUser(name,password,salt);
    }
}
