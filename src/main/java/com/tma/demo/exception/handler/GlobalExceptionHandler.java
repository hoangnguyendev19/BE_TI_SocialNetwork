package com.tma.demo.exception.handler;

import com.tma.demo.dto.ApiResponse;
import com.tma.demo.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler
 * Version 1.0
 * Date: 04/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 04/10/2024        NGUYEN             create
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Object>> handlerBaseException(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatusCode.valueOf(e.getErrorCode().getCode()))
                .body(new ApiResponse<>(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> handlerBindException(BindException e) {
        String errorMessage = "invalid request!";
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        }

        return ResponseEntity.badRequest().body(new ApiResponse<>(400, errorMessage, null) );
    }
}
