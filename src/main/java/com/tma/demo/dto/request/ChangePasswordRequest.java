package com.tma.demo.dto.request;

import com.tma.demo.constant.PatternConstant;
import com.tma.demo.constant.ValidateFieldMessage;
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
            regexp = PatternConstant.PASSWORD,
            message = ValidateFieldMessage.PASSWORD
    )
    private String currentPassword;
    @Pattern(
            regexp = PatternConstant.PASSWORD,
            message = ValidateFieldMessage.PASSWORD
    )
    private String newPassword;

    private String confirmNewPassword;
}
