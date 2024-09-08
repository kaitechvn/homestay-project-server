package com.example.vmacademy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateException extends RuntimeException {

    private String code;

    public AuthenticateException(String code, String message) {
        super(message);
        this.code = code;
    }
}
