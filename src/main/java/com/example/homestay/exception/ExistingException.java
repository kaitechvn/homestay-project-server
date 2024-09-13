package com.example.homestay.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingException extends AbstractApiException{

    public ExistingException(ErrorCode errorCode) {
        super(errorCode);
    }

}
