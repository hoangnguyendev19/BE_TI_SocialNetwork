package com.tma.demo.common;

import lombok.Getter;

@Getter
public enum SuccessMessage {
    LOGIN_SUCCESS("login-successfully"),
    CHANGE_PASSWORD_SUCCESS("change-password-successfully"),
    UPDATE_PASSWORD_SUCCESS("update-password-successfully"),
    UPDATE_PROFILE_SUCCESS("update-profile-successfully"),
    CHANGE_AVATAR_SUCCESS("change-avatar-successfully"),
    GET_USER_SUCCESS("get-user-successfully"),
    REGISTER_SUCCESS("register-successfully"),
    CREATED_POST_SUCCESS("create-post-successfully"),
    OTP_VERIFY("otp_verify-successfully"),
    OTP_SEND("otp_send-successfully"),
    DELETE_COMMENT_SUCCESS("delete-comment-successfully"),
    UPDATE_COMMENT_SUCCESS("update-comment-successfully"),
    VIEW_COMMENT_SUCCESS("view-comment-successfully"),
    CREATED_COMMENT_SUCCESS("create-comment-successfully"),;
    private final String message;
    SuccessMessage(String s) {
        this.message = s;
    }
}
