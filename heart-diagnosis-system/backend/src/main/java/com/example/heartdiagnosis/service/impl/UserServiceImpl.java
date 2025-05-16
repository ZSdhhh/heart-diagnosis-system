package com.example.heartdiagnosis.service.impl;

import com.example.heartdiagnosis.entity.User;
import com.example.heartdiagnosis.mapper.UserMapper;
import com.example.heartdiagnosis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }
    
    @Override
    public boolean register(User user) {
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            return false;
        }
        
        if (user.getRole() == null) {
            user.setRole("user");
        }
        
        return userMapper.register(user) > 0;
    }
} 