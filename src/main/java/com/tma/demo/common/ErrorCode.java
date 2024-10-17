package com.tma.demo.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(), "wrong-password"),
    CONFIRM_PASSWORD_DOES_NOT_MATCH(HttpStatus.BAD_REQUEST.value(), "confirm-password-does-not-match"),
    USER_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "user-does-not-exist"),
    UNAUTHENTICATED(HttpStatus.UNAUTHORIZED.value(), "unauthenticated"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED.value(), "token-expired"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED.value(), "token-invalid"),
    IMAGE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST.value(), "image-upload-failed"),
    EMAIL_EXIST(HttpStatus.BAD_REQUEST.value(), "email-already-exists."),
    MATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), "passwords-do-not-match."),
    UNABLE_SEND_OTP(HttpStatus.BAD_REQUEST.value(), "Unable to send otp please try again"),
    OTP_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "otp-does-not-exist"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST.value(), "otp-expired"),
    POST_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "post-does-not-exist"),
    COMMENT_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "comment-does-not-exist"),
    UPDATE_COMMENT_ERROR(HttpStatus.BAD_REQUEST.value(), "update-comment-error"),
    DELETE_COMMENT_ERROR(HttpStatus.BAD_REQUEST.value(), "delete-comment-error"),
    DELETE_FILE_FAILED(HttpStatus.BAD_REQUEST.value(), "delete-file-failed"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "bad-request"),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "post-not-found"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "unauthorized"),
    WRITE_BASE64_TO_IMAGE_ERROR(HttpStatus.BAD_REQUEST.value(), "write-base64-to-image-error"),;
    private final int code;
    private final String message;

    ErrorCode(int i, String s) {
        this.code = i;
        this.message = s;
    }
}