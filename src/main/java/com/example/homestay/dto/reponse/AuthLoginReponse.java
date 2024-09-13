package com.example.homestay.dto.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthLoginReponse {

    private String accessToken;
    private String refreshToken;

}
