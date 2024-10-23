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
    UNABLE_SEND_OTP(HttpStatus.BAD_REQUEST.value(), "unable-to-send-otp"),
    OTP_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "otp-does-not-exist"),
    OTP_EXPIRED(HttpStatus.BAD_REQUEST.value(), "otp-expired"),
    POST_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "post-does-not-exist"),
    COMMENT_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "comment-does-not-exist"),
    UPDATE_COMMENT_ERROR(HttpStatus.BAD_REQUEST.value(), "update-comment-error"),
    DELETE_COMMENT_ERROR(HttpStatus.BAD_REQUEST.value(), "delete-comment-error"),
    LIKE_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "like-does-not-exist"),
    DELETE_FILE_FAILED(HttpStatus.BAD_REQUEST.value(), "delete-file-failed"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "bad-request"),
    POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "post-not-found"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "unauthorized"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), "forbidden"),
    SETTING_KEY_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST.value(), "setting-key-does-not-exist"),
    NOT_BASE64_FORMAT(HttpStatus.BAD_REQUEST.value(), "not-base64-format"),
    BOARDING_HOUSE_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "boarding-name-already-exists"),
    NEW_PASSWORD_EQUALS_CURRENT_PASSWORD(HttpStatus.BAD_REQUEST.value(), "new-password-equals-current-password"),
    SETTING_KEY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "setting-key-already-exists"),
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST.value(), "file-upload-failed"),
    ROOM_NAME_ALREADY_EXIST(HttpStatus.BAD_REQUEST.value(), "room-name-already-exists"),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "room-not-found"),
    PAYMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "payment-not-found"),
    ROOM_SETTING_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "room-setting-not-found"),
    ;
    private final int code;
    private final String message;

    ErrorCode(int i, String s) {
        this.code = i;
        this.message = s;
    }
}