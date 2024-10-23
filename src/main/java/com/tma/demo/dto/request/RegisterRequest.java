package com.tma.demo.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.tma.demo.constant.Forgot_Register_Constant;
@AllArgsConstructor
@Data
public class RegisterRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Pattern(
            regexp = Forgot_Register_Constant.EMAIL_PATTERN ,
            message = "email-not-correct-format"
    )
    private String email;
    @Pattern(
            regexp = Forgot_Register_Constant.PHONE_NUMBER_PATTERN ,
            message = "invalid-phone-number."
    )
    private String phoneNumber;
    @Pattern(
            regexp = Forgot_Register_Constant.PASSWORD_PATTERN ,
            message = "password-invalid"
    )
    private String password;
    
    private String confirmPassword;

}
