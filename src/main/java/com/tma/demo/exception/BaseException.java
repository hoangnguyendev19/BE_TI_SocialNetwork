package com.tma.demo.exception;

import com.tma.demo.common.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * BaseException
 * Version 1.0
 * Date: 04/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 04/10/2024        NGUYEN             create
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private ErrorCode errorCode;
}
