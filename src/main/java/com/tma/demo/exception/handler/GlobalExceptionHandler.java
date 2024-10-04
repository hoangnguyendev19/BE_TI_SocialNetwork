package com.tma.demo.exception.handler;

import com.tma.demo.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<String> handlerBaseException(BaseException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<String> handlerBindException(BindException e) {
        String errorMessage = "invalid request!";
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        }

        return ResponseEntity.badRequest().body(errorMessage );

    }
}
