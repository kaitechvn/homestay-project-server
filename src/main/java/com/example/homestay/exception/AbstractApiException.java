package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public abstract class AbstractApiException extends RuntimeException {

    private ErrorCode errorCode;

    public AbstractApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
