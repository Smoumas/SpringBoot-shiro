package com.shiro.service;

import com.shiro.domain.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findUserByName(String name);
    User findUserByID(Integer id);
    void register(String name,String password);
}
