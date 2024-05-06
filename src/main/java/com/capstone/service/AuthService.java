package com.capstone.service;

import com.capstone.dto.ChangePasswordDto;
import com.capstone.dto.JwtAuthResponse;
import com.capstone.dto.LoginDto;
import com.capstone.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    JwtAuthResponse login(LoginDto loginDto);
    String changePassword(ChangePasswordDto changePasswordDto);
}
