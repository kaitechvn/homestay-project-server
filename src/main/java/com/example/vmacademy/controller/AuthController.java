package com.example.vmacademy.controller;

import com.example.vmacademy.dto.ApiResponse;
import com.example.vmacademy.dto.reponse.AuthLoginReponse;
import com.example.vmacademy.dto.request.AuthLoginRequest;
import com.example.vmacademy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthLoginReponse>> login(@RequestBody @Valid AuthLoginRequest loginRequest) {
        return ResponseEntity.ok(
                new ApiResponse<>("Login success", authService.login(loginRequest)));
    }
//
//    @PostMapping("/logout")
//    public ResponseEntity
}
