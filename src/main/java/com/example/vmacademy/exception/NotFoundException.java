package com.example.vmacademy.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotFoundException extends RuntimeException {

    private String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }
}
