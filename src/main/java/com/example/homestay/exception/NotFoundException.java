package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotFoundException extends ApiException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
