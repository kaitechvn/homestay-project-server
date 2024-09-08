package com.example.vmacademy.service;

import com.example.vmacademy.dto.reponse.AuthLoginReponse;
import com.example.vmacademy.dto.request.AuthLoginRequest;
import com.example.vmacademy.dto.request.AuthRegisterRequest;

public interface AuthService {

    AuthLoginReponse login(AuthLoginRequest authLoginRequest);

    void register(AuthRegisterRequest authRegisterRequest);

    void forgotPassword(String email);

}
