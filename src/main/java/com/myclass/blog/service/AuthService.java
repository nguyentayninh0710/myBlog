package com.myclass.blog.service;

import com.myclass.blog.payload.LoginDto;
import com.myclass.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
