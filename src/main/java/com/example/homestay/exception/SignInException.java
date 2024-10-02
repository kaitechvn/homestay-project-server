package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInException extends ApiException {

    public SignInException(ErrorCode errorCode) {
        super(errorCode);
    }
}
