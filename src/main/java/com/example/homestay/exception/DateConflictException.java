package com.example.homestay.exception;

public class DateConflictException extends RuntimeException {
  public DateConflictException(String message) {
    super(message);
  }
}