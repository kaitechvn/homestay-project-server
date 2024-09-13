package com.example.homestay.controller;

import com.example.homestay.dto.ApiResponse;
import com.example.homestay.dto.reponse.AuthLoginReponse;
import com.example.homestay.dto.request.AuthLoginRequest;
import com.example.homestay.service.AuthService;
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
                new ApiResponse<>("Login successfully", authService.login(loginRequest)));
    }

}
