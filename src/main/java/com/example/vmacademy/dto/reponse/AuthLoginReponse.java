package com.example.vmacademy.dto.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthLoginReponse {

    private String tokenType;
    private String accessToken;
    private String deviceToken;
}
