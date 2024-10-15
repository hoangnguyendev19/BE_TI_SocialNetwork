package com.tma.demo.configuration.security;

import com.tma.demo.common.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * JwtEntryPoint
 * Version 1.0
 * Date: 14/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/10/2024        NGUYEN             create
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(ErrorCode.BAD_REQUEST.getCode());
        response.setContentType("application/json");
        response.getWriter().write(ErrorCode.BAD_REQUEST.getMessage());
        response.getWriter().flush();
    }
}
