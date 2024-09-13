package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInException extends AbstractApiException {

    public SignInException(ErrorCode errorCode) {
        super(errorCode);
    }
}
