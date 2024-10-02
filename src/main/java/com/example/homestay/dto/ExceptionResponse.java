package com.example.homestay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionResponse {

    private String type;
    private String code;
    private String message;
}
