package com.capstone.service.impl;


import com.capstone.dto.ChangePasswordDto;
import com.capstone.dto.JwtAuthResponse;
import com.capstone.dto.LoginDto;
import com.capstone.dto.RegisterDto;
import com.capstone.entity.User;
import com.capstone.exception.DuplicateException;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.repository.UserRepository;
import com.capstone.security.JwtTokenProvider;
import com.capstone.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {
        //check if username and password already exists in database.
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new DuplicateException("Username already exists");
        }
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = jwtTokenProvider.generateToken(authenticate);

        // get the role of logged in user from database
        Optional<User> userOptional = userRepository.findByEmail(
                 loginDto.getEmail());

        String role = null;
        if(userOptional.isPresent()){
            User loggedIn = userOptional.get();
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

            jwtAuthResponse.setAccessToken(token);
            return jwtAuthResponse;
        }else{
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with the given username"));

        if(passwordEncoder.matches( changePasswordDto.getOldPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            return "Password changed successfully";
        }else{
            throw new RuntimeException("Incorrect old password");
        }
    }
}