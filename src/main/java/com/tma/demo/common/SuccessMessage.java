package com.tma.demo.common;

import lombok.Getter;

@Getter
public enum SuccessMessage {
    LOGIN_SUCCESS("login-successfully"),
    CHANGE_PASSWORD_SUCCESS("change-password-successfully"),
    UPDATE_PROFILE_SUCCESS("update-profile-successfully"),
    CHANGE_AVATAR_SUCCESS("change-avatar-successfully"),
    GET_USER_SUCCESS("get-user-successfully"),
    CREATED_POST_SUCCESS("create-post-successfully"),
    UPDATE_POST_SUCCESS("update-post-successfully");
    private final String message;
    SuccessMessage(String s) {
        this.message = s;
    }
}
