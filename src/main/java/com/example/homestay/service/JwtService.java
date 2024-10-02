package com.example.homestay.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public interface JwtService {

    String generateAccessToken(String username, List<String> roles);

    String generateRefreshToken(String username);

    List<String> extractRoles(UserDetails userDetails);
}
