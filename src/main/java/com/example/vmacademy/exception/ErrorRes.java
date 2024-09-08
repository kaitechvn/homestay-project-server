package com.example.vmacademy.exception;

import lombok.*;

@Getter
@Setter
@Builder
public class ErrorRes {

    private String code;
    private String message;
    private String timestamp;
    private String path;

}
