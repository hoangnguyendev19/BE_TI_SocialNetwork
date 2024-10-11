package com.tma.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * LoginRequest
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "email address invalid")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$",
            message = "password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character."
    )
    private String password;
}
