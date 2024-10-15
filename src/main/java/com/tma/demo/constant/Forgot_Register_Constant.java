package com.tma.demo.constant;

public class Forgot_Register_Constant {
    public static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{8,}$";
    public static final String PHONE_NUMBER_PATTERN =
            "^\\+?(\\d{1,3})?[-.\\s]?(\\d{2,4})[-.\\s]?(\\d{3,4})[-.\\s]?(\\d{3,4})$";
    public static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

}
