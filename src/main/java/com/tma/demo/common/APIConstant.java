package com.tma.demo.common;

public class APIConstant {
    public static final String BASE_API = "/api/v1";
    public static final String POSTS = BASE_API + "/posts";
    public static final String USERS = BASE_API + "/users";
    public static final String COMMENTS = BASE_API + "/comment";
    public static final String AUTH = BASE_API + "/auth";
    // Auth Endpoints
    public static final String AUTH_LOGIN = "/login";
    public static final String AUTH_REGISTER = "/register";
    public static final String AUTH_VERIFY_OTP = "/verify-otp";
    public static final String AUTH_FORGOT_PASSWORD = "/forgot-password";
    public static final String AUTH_SET_PASSWORD = "/set-password";
    // User Endpoints
    public static final String USER_AVATAR = "/avatar";
    public static final String USER_PASSWORD = "/password";
    // Post Endpoints
    public static final String DELETE_POST = "/{postId}";
    public static final String GET_POST_NEWS = "/news";
    // Comment Post Endpoints
    public static final String CREATE_COMMENT_POST = "/create";
    public static final String UPDATE_COMMENT_POST = "/update";
    public static final String DELETE_COMMENT_POST = "/delete";
    public static final String VIEW_LIST_COMMENT_POST = "/view-list";
    public static final String HIDDEN_LIST_COMMENT_POST = "/hidden";


}
