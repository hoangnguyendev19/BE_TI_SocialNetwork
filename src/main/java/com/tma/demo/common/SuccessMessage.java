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
    CREATED_POST_SUCCESS("create-post-successfully"),
    UPDATE_POST_SUCCESS("update-post-successfully"),
    REGISTER_SUCCESS("register-successfully"),
    OTP_VERIFY("otp_verify-successfully"),
    OTP_SEND("otp_send-successfully"),
    DELETE_COMMENT_SUCCESS("delete-comment-successfully"),
    UPDATE_COMMENT_SUCCESS("update-comment-successfully"),
    CREATED_COMMENT_SUCCESS("create-comment-successfully"),
    DELETE_POST_SUCCESS("delete-post-successfully"),
    GET_NEWS_SUCCESS("get-news-successfully"),
    REPORT_POST_SUCCESS("report-post-successfully"),
    CREATED_SETTING_SUCCESS("create-setting-successfully"),
    UPDATE_SETTING_SUCCESS("update-setting-successfully"),
    CREATE_FAVOURITE_POST_SUCESS("create-favourite-post-successfully"),
    ;

    private final String message;

    SuccessMessage(String s) {
        this.message = s;
    }
}
