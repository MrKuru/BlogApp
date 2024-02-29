package com.springboot.blog.service;

import com.springboot.blog.model.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
