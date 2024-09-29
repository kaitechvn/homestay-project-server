package com.example.homestay.security;

import com.example.homestay.dto.ExceptionResponse;
import com.example.homestay.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@Log4j2
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String message = authException.getMessage();
        String errorCode = ErrorCode.AUTHENTICATION_REQUIRED.getCode();

        if (message.contains("expired")) {
            errorCode = ErrorCode.JWT_EXPIRED.getCode();
            message = ErrorCode.JWT_EXPIRED.getMessage();
        }

        ExceptionResponse exResponse = ExceptionResponse.builder()
                .type(authException.getClass().getSimpleName())
                .message(message)
                .code(errorCode)
                .build();

        // Convert ErrorRes to JSON
        String jsonResponse = objectMapper.writeValueAsString(exResponse);

        // Write JSON to response
        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}
