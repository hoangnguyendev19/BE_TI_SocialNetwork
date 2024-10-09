package com.tma.demo.service.validator;

import org.springframework.stereotype.Service;

import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.service.RegisterService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


@Service
public class RegisterValidator implements ConstraintValidator<RegisterChecked, RegisterRequest> {

    private final RegisterService registerService;

    public RegisterValidator(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Override
    public boolean isValid(RegisterRequest user, ConstraintValidatorContext context) {
        boolean valid = true;

        // Check if password fields match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            context.buildConstraintViolationWithTemplate("Passwords nhập không chính xác")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        // Additional validations can be added here
        // check email
        if (this.registerService.isEmailExist(user.getEmail())) {
            context.buildConstraintViolationWithTemplate("Email đã tồn tại")
                    .addPropertyNode("email")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
