package com.example.homestay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_BLANK(1001, "Username cannot be blank"),
    PASSWORD_BLANK(1002, "Password cannot be blank"),
    PASSWORD_INVALID(1003, "Password is invalid"),
    EMAIL_BLANK(1004, "Email cannot be blank"),
    EMAIL_INVALID(1005, "Email is invalid"),
    PHONE_BLANK(1006, "Phone number cannot be blank"),
    PHONE_INVALID(1007, "Phone number is invalid"),
    DOB_BLANK(1008, "Date of birth cannot be blank"),
    USER_NOT_FOUND(1009, "User not found"),
    USER_EXISTED(1010, "User already exists"),
    EMAIL_EXISTED(1011, "Email already exists");

    private final Integer code;
    private final String message;


}
