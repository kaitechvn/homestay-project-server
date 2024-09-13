package com.example.homestay.service;

import com.example.homestay.dto.reponse.AuthLoginReponse;
import com.example.homestay.dto.request.AuthLoginRequest;
import com.example.homestay.dto.request.AuthRegisterRequest;

public interface AuthService {

    AuthLoginReponse login(AuthLoginRequest authLoginRequest);

    void register(AuthRegisterRequest authRegisterRequest);

    void forgotPassword(String email);

}
