package com.example.homestay.utils;

import com.example.homestay.model.User;
import com.example.homestay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    public boolean isUserOwner(String username, Integer id) {
        User user = userRepository.findById(id).orElse(null);

        // If the user exists and the authenticated user's name matches, return true
        return user != null && user.getUsername().equals(username);
    }
}