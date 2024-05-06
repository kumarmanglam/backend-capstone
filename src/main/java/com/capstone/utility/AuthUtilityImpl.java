package com.capstone.utility;

import com.capstone.entity.User;
import com.capstone.exception.ResourceNotFoundException;
import com.capstone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthUtilityImpl implements AuthUtility{
    UserRepository userRepository;
    @Override
    public User getUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        System.out.println("----->" + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No user found with the given username"));
        return user;
    }
}
