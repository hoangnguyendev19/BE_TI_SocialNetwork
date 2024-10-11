package com.tma.demo.controller;

import com.tma.demo.common.TokenType;
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

/**
 * AuthenticationController
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE AUTHOR DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024 NGUYEN create
 */
@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final ForgotPassService forgotPassService;
    private final RegisterService registerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(), " login successfully", authService.authenticate(request)));
    }

    //
    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {
        try {
            User user = this.registerService.registerDTOtoUser(registerRequest);

            String hashPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);

            RegisterResponse RegisterResponse = this.registerService.saveUser(user);

            // Tạo phản hồi thành công
            ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "Register Success", // Thông báo thành công
                    RegisterResponse //
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), // Thông báo lỗi từ service
                    null);
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    // API VERIFY OTP
    @PutMapping(value = "/verify-otp")
    public ResponseEntity<ApiResponse<VerifyOtpResponse>> verifyAccount(
            @RequestBody VerifyOTPRequest verifyOTPRequest) {
        try {
            // Gọi service để xác thực OTP và nhận phản hồi

            // String token = jwtService.generateToken(verifyOTPRequest.getEmail(), TokenType.ACCESS_TOKEN);
            VerifyOtpResponse verifyOtpResponse = forgotPassService.verifyAccount(
                verifyOTPRequest.getEmail(), 
                verifyOTPRequest.getOtp()
        );

            ApiResponse<VerifyOtpResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "OTP verification successful",
                    verifyOtpResponse);

            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            ApiResponse<VerifyOtpResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null // Không có dữ liệu phản hồi khi thất bại
            );

            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    // API FORGOT PASSWORD
    @PostMapping(value = "/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        try {
            String otp = forgotPassService.generateOtp(request.getEmail());

            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "OTP has been sent to your Email",
                    otp // Trả về mã OTP trong `data`
            );

            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), // Thông báo lỗi từ service
                    null // Không cần dữ liệu bổ sung
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }

    }

    // API SET PASSWORD
    @PutMapping(value = "/set-password/{email}/{otp}")
    public ResponseEntity<ApiResponse<String>> setPassword(
            @PathVariable String email,
            @PathVariable String otp,
            @Valid @RequestBody SetPasswordRequest setPasswordRequest) {
        try {
            String response = forgotPassService.setPassword(email, otp, setPasswordRequest);
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    response, // Thông báo thành công
                    null // Không cần dữ liệu bổ sung
            );
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(), // Thông báo lỗi từ service
                    null // Không cần dữ liệu bổ sung
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }
}
