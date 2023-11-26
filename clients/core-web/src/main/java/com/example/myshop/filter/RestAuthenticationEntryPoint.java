package com.example.myshop.filter;


import com.example.myshop.exception.ErrorCode;
import com.example.myshop.response.APIErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode commonUnauthorized = ErrorCode.COMMON_UNAUTHORIZED;
        APIErrorResponse fail = APIErrorResponse.fail(commonUnauthorized);
        ObjectMapper objectMapper = new ObjectMapper();
        String out = objectMapper.writeValueAsString(fail);

        response.setContentType("application/json");
        response.setStatus(commonUnauthorized.getStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(out);
        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());
    }
}
