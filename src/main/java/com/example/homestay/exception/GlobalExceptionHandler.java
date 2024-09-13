package com.example.homestay.exception;

import com.example.homestay.utils.TimeUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    private static final String EX_LOG_FORMAT = "URI: {}, Code: {}, Message: {}";

    private ResponseEntity<ErrorCode> handleException(AbstractApiException exception) {
        ErrorCode errorCode = exception.getErrorCode();

//        String uri = request.getRequestURI();
        log.debug(EX_LOG_FORMAT, exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.badRequest().body(errorCode);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<ErrorCode> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
//
//        FieldError fieldError = (FieldError) ex.getBindingResult().getAllErrors().get(0);
//        ex.getLocalizedMessage()
//        String code = fieldError.getDefaultMessage();
//
//        return handleException("argument_not_valid", message, request);
//    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorCode> handleNotFound(NotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.badRequest().body(errorCode);
    }

    @ExceptionHandler(ExistingException.class)
    protected ResponseEntity<ErrorCode> handleExisting(ExistingException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.badRequest().body(errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorCode> handleValidation(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        return ResponseEntity.badRequest().body(errorCode);
    }




}






