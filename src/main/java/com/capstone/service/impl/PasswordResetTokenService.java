package com.capstone.service.impl;

import com.capstone.entity.PasswordResetToken;
import com.capstone.entity.User;
import com.capstone.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken createToken(User user) {
        PasswordResetToken existingToken = tokenRepository.findByUser(user);

        // If an existing token is found, delete it
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); // Expiration time: 1 hour
        return tokenRepository.save(passwordResetToken);

        // if token already exists for same user then delete that token and re insert the new token
    }

    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
}
