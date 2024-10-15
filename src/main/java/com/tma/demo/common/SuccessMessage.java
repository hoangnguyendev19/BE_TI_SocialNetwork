package com.tma.demo.common;

import lombok.Getter;

@Getter
public enum SuccessMessage {
    LOGIN_SUCCESS("login-successfully"),
    CHANGE_PASSWORD_SUCCESS("change-password-successfully"),
    UPDATE_PROFILE_SUCCESS("update-profile-successfully"),
    CHANGE_AVATAR_SUCCESS("change-avatar-successfully"),
    GET_USER_SUCCESS("get-user-successfully"),
    REGISTER_SUCCESS("register-successfully"),
    OTP_VERIFY("otp_verify-successfully"),
    CREATED_POST_SUCCESS("create-post-successfully"),;
    private final String message;
    SuccessMessage(String s) {
        this.message = s;
    }
}
