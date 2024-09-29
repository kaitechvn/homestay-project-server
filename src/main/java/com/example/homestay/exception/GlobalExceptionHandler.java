package com.example.homestay.exception;

import com.example.homestay.dto.ExceptionResponse;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    private static final String EX_LOG_FORMAT = "Type: {}, Code: {}, Message: {}";

    @ExceptionHandler(ApiException.class)
    private ResponseEntity<ExceptionResponse> handleException(ApiException ex) {

        ExceptionResponse response = ExceptionResponse.builder()
                .type(ex.getClass().getSimpleName())
                .code(ex.getErrorCode().getCode())
                .message(ex.getErrorCode().getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException ex) {
        String enumKey = ex.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ExceptionResponse response = ExceptionResponse.builder()
                .type(ex.getClass().getSimpleName())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ImageSizeException.class)
    public ResponseEntity<ExceptionResponse> handleImageSizeException(ImageSizeException ex) {
        ExceptionResponse response = new ExceptionResponse(
                ex.getClass().getSimpleName(),
                ex.getErrorCode().getCode(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ExceptionResponse> handleAccessDenied(AccessDeniedException ex) {

        ExceptionResponse response = ExceptionResponse.builder()
                .type(ex.getClass().getSimpleName())
                .code(ErrorCode.FORBIDDEN.getCode())
                .message(ErrorCode.FORBIDDEN.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
}






