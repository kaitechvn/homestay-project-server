package com.example.homestay.service.auth;

import com.example.homestay.dto.reponse.AuthResponse;
import com.example.homestay.dto.request.AuthLoginRequest;
import com.example.homestay.dto.request.AuthRegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    AuthResponse login(AuthLoginRequest authLoginRequest, HttpServletResponse response);

    void register(AuthRegisterRequest authRegisterRequest);

    void forgotPassword(String email);

    AuthResponse refreshToken(HttpServletRequest request);

}
