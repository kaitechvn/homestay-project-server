package com.example.homestay.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {


        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());
////
//        ErrorRes errorResponse = ErrorRes.builder()
//                .message(accessDeniedException.getMessage())
//                .code(AUTHORIZATION_ERROR)
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

