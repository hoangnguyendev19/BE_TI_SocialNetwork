package com.tma.demo.dto.request;

import com.tma.demo.service.validator.RegisterChecked;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@RegisterChecked
@Data
public class RegisterRequest {
    @NotBlank(message = "Name không được để trống")
    private String FirstName;
    @NotBlank(message = "Name không được để trống")
    private String LastName;
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email not correct format")
    private String email;
    @Pattern(regexp = "^\\+?(\\d{1,3})?[-.\\s]?(\\d{2,4})[-.\\s]?(\\d{3,4})[-.\\s]?(\\d{3,4})$", message = "Invalid phone number")
    private String phoneNumber;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$", message = "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character.")
    private String password;

    private String ConfirmPassword;
}
