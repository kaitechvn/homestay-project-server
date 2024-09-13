package com.example.homestay.security;

import com.example.homestay.utils.TimeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    private String message;
    private String errorCode;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//        message = authException.getMessage();;
//        errorCode = AUTHENTICATION_ERROR;
//
//        if (message.contains("expired")) {
//            errorCode = JWT_EXPIRED_ERROR;
//            message = "access token has expired";
//        }
//
//        ErrorRes errorResponse = ErrorRes.builder()
//                .message(message)
//                .code(errorCode)
//                .path(request.getRequestURI())
//                .timestamp(TimeUtils.getTimestamp())
//                .build();
//
//        // Convert ErrorRes to JSON
//        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
//
//        // Write JSON to response
//        response.getWriter().write(jsonResponse);
//        response.getWriter().flush();
    }
}
