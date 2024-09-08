package com.example.vmacademy.exception;

import com.example.vmacademy.dto.ApiResponse;
import com.example.vmacademy.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.example.vmacademy.utils.Constants.StatusCode.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler  {

    private static final String ERROR_LOG_FORMAT = "Timestamp: {}, URI: {}, ErrorCode: {}, Message: {}";

    private ResponseEntity<ErrorRes> responseEntity(String code, String msg, WebRequest request) {
        return ResponseEntity.badRequest().body(
                ErrorRes.builder()
                        .code(code)
                        .path(this.getServletPath(request))
                        .timestamp(Instant.now().atOffset(ZoneOffset.UTC)
                                    .format(DateTimeFormatter.ISO_DATE_TIME))
                        .message(msg)
                        .build()
        );
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//
//        FieldError fieldError = (FieldError) ex.getBindingResult().getAllErrors().get(0); // Get the first error
//        String errorCode = fieldError.getDefaultMessage(); // Key for message retrieval
//
//        ApiException apiException = new ApiException(errorCode, "Validation Error");
//
//        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorRes> handleNotFound(ExistingException e, WebRequest request) {

//        log.debug(ERROR_LOG_FORMAT, this.getServletPath(request), errorCode, e.getMessage());
        return responseEntity(e.getCode(), e.getMessage(), request);
    }

    @ExceptionHandler(ExistingException.class)
    protected ResponseEntity<ErrorRes> handleExisting(ExistingException e, WebRequest request) {

//        log.debug(ERROR_LOG_FORMAT, this.getServletPath(request), errorCode, e.getMessage());
        return responseEntity(e.getCode(), e.getMessage(), request);
    }

    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }

}




