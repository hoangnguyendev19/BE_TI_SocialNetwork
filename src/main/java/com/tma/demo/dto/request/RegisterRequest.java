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
            message = "Email not correct format."
    )
    private String email;
    @Pattern(
            regexp = Forgot_Register_Constant.PHONE_NUMBER_PATTERN ,
            message = "Invalid  PhoneNumber."
    )
    private String phoneNumber;
    @Pattern(
            regexp = Forgot_Register_Constant.PASSWORD_PATTERN ,
            message = "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character."
    )
    private String password;
    
    private String confirmPassword;

}
