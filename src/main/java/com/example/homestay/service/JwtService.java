package com.example.homestay.service;

import org.springframework.stereotype.Service;

import java.util.List;

public interface JwtService {

    String generateAccessToken(String username, List<String> roles);

    String generateRefreshToken(String username);
}
