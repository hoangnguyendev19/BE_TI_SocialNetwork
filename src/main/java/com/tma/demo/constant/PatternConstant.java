package com.tma.demo.constant;

/**
 * PatternConstant
 * Version 1.0
 * Date: 14/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/10/2024        NGUYEN             create
 */
public class PatternConstant {
    public static final String PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$";
    public static final String EMAIL_ADDRESS = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
}
