package com.example.homestay.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {

    USERNAME_BLANK("username_blank", "Username cannot be blank"),
    PASSWORD_BLANK("password_blank", "Password cannot be blank"),
    PASSWORD_INVALID("password_invalid", "Password is invalid"),
    EMAIL_BLANK("email_blank", "Email cannot be blank"),
    EMAIL_INVALID("email_invalid", "Email is invalid"),
    PHONE_BLANK("phone_blank", "Phone number cannot be blank"),
    PHONE_INVALID("phone_invalid", "Phone number is invalid"),
    PHONE_EXISTED("phone_existed", "Phone number already existed"),
    DOB_BLANK("dob_blank", "Date of birth cannot be blank"),
    USER_NOT_FOUND("user_not_found", "User not found"),
    USER_EXISTED("user_existed", "User already exists"),
    EMAIL_EXISTED("email_existed", "Email already exists"),

    // Homestay
    HOMESTAY_NOT_FOUND("homestay_not_found", "Homestay not found"),

    // Images
    IMAGE_NOT_FOUND("image_not_found", "Image not found"),
    IMAGE_SIZE_EXCEED("image_size_exceed", "Image size exceed limit"),

    // Booking
    BOOKING_NOT_FOUND("booking_not_found", "Booking not found"),

    // Transaction
    TRANSACTION_NOT_FOUND("transaction_not_found", "Transaction not found"),

    // JWT-Auth
    AUTHENTICATION_REQUIRED("unauthorized", "The Authentication header is missing, " +
                            "or is incorrectly specified, or is an invalid token"),
    JWT_EXPIRED("token_expired", "Token expired"),
    FORBIDDEN("forbidden", "Don't have permission"),

    // Input
    DATE_INVALID("date_invalid", "Check again check-in, check-out input");

    private final String code;
    private final String message;


}
