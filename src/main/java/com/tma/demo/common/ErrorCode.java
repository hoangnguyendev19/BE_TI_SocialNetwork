package com.tma.demo.common;

import lombok.Getter;

/**
 * ErrorCode
 * Version 1.0
 * Date: 09/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 09/10/2024        NGUYEN             create
 */
@Getter
public enum ErrorCode {
    WRONG_PASSWORD(400, "wrong-password"),
    CONFIRM_PASSWORD_DOES_NOT_MATCH(400, "confirm-password-does-not-match"),
    USER_DOES_NOT_EXIST(400, "user-does-not-exist"),
    UNAUTHENTICATED(401, "unauthenticated"),
    TOKEN_EXPIRED(401, "token-expired"),
    TOKEN_INVALID(401, "token-invalid")

    ;
    private final int code;
    private final String message;


    ErrorCode(int i, String s) {
        this.code = i;
        this.message = s;


    }
}
