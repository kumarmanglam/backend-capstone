package com.capstone.service;

import com.capstone.entity.User;

public interface UserService {
    User getUserById(Long userId);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void saveUser(User user);
    void updateUser(User user);
}
