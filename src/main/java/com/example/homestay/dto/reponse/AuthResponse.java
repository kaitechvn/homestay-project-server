package com.example.homestay.dto.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
}
