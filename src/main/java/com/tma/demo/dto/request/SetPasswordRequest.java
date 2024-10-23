package com.tma.demo.dto.request;

import com.tma.demo.constant.Forgot_Register_Constant;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetPasswordRequest {
    @Pattern(
            regexp = Forgot_Register_Constant.EMAIL_PATTERN ,
            message = "email-not-correct-format"
    )
    private String email;
    @Pattern(
            regexp = Forgot_Register_Constant.PASSWORD_PATTERN ,
            message = "password-invalid"
    )
    private String newPassword;

    private String confirmNewPassword;
}
