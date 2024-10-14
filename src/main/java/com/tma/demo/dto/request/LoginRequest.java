package com.tma.demo.dto.request;

import com.tma.demo.constant.PatternConstant;
import com.tma.demo.constant.ValidateFieldMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Email(regexp = PatternConstant.EMAIL_ADDRESS,
            message = ValidateFieldMessage.EMAIL_ADDRESS)
    private String email;

    @Pattern(
            regexp = PatternConstant.PASSWORD,
            message = ValidateFieldMessage.PASSWORD
    )
    private String password;
}
