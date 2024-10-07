package com.tma.demo.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * ChangePasswordRequest
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$",
            message = "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character."
    )
    private String currentPassword;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$",
            message = "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character."
    )
    private String newPassword;

    private String confirmNewPassword;
}
