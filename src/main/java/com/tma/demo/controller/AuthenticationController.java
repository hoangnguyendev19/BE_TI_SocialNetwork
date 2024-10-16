package com.tma.demo.controller;


import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.ForgotPasswordRequest;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.request.VerifyOTPRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.dto.response.VerifyOtpResponse;
import com.tma.demo.entity.User;
import com.tma.demo.service.auth.AuthService;
import com.tma.demo.service.auth.ForgotPassService;
import com.tma.demo.service.auth.RegisterService;
import com.tma.demo.service.jwt.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.APIConstant.*;

/**
 * AuthenticationController
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = AUTH)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final ForgotPassService forgotPassService;
    private final RegisterService registerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping(value = AUTH_LOGIN)
    public ResponseEntity<ApiResponse<TokenDto>> login(
            @RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        SuccessMessage.LOGIN_SUCCESS.getMessage(),
                        authService.authenticate(request)));
    }

    @PostMapping(value = AUTH_REGISTER)
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {

            User user = this.registerService.registerDTOtoUser(registerRequest);

            String hashPassword = this.passwordEncoder.encode(user.getPassword());

            user.setPassword(hashPassword);

            RegisterResponse RegisterResponse = this.registerService.saveUser(user);
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.CREATED.value(),
                        SuccessMessage.REGISTER_SUCCESS.getMessage(),
                                RegisterResponse));
    }
    // API VERIFY OTP
    @PutMapping(value = AUTH_VERIFY_OTP)
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verifyAccount(
            @RequestBody VerifyOTPRequest verifyOTPRequest) {
            // String token = jwtService.generateToken(verifyOTPRequest.getEmail(),
            // TokenType.ACCESS_TOKEN);
            VerifyOtpResponse verifyOtpResponse = forgotPassService.verifyAccount(
                    verifyOTPRequest.getEmail(),
                    verifyOTPRequest.getOtp());
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                    SuccessMessage.OTP_VERIFY.getMessage(),
                    verifyOtpResponse));
    }
    // API FORGOT PASSWORD
    @PostMapping(value = AUTH_FORGOT_PASSWORD)
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        String otp = forgotPassService.generateOtp(request.getEmail());

            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                    otp,
                    null ));
    }
    // API SET PASSWORD
    @PutMapping(value = AUTH_SET_PASSWORD)
    public ResponseEntity<ApiResponse<String>> setPassword(
            @Valid @RequestBody SetPasswordRequest setPasswordRequest) {

        String response = forgotPassService.setPassword(setPasswordRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                response,
                null ));
    }
}
