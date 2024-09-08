package com.example.vmacademy.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingException extends RuntimeException {

    private String code;

    public ExistingException(String code, String message) {
        super(message);
        this.code = code;
    }
}
