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
            message = "Email not correct format."
    )
    private String email;
    @Pattern(
            regexp = Forgot_Register_Constant.PASSWORD_PATTERN ,
            message = "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character."
    )
    private String newPassword;

    private String confirmNewPassword;
}
