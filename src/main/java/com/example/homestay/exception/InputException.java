package com.example.homestay.exception;

public class InputException extends ApiException {
    public InputException(ErrorCode errorCode) {

      super(errorCode);
    }
}
