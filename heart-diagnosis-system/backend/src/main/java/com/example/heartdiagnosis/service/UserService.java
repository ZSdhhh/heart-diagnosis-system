package com.example.heartdiagnosis.service;

import com.example.heartdiagnosis.entity.User;

public interface UserService {
    
    User login(String username, String password);
    
    boolean register(User user);
} 