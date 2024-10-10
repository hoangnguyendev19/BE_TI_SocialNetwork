package com.tma.demo.controller;

import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.entity.User;
import com.tma.demo.service.AuthService;
import com.tma.demo.service.ForgotPassService;
import com.tma.demo.service.RegisterService;
import com.tma.demo.service.UserService;

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
                    HttpStatus.CREATED,
                    "Register Success", // Thông báo thành công
                    RegisterResponse // 
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<RegisterResponse> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(), // Thông báo lỗi từ service
                    null 
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK, " login successfully", authService.authenticate(request)));
    }

    // API VERIFY OTP
    @PutMapping(value = "/verify-otp")
    public ResponseEntity<ApiResponse<String>> verifyAccount(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "otp") String otp) {
        try {
            String message = forgotPassService.verifyAccount(email, otp);

            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.OK,
                    message, // Thông báo từ service
                    null //
            );
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {

            // Tạo phản hồi lỗi với thông báo
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(), // Thông báo lỗi từ service
                    null // 
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

    // API FORGOT PASSWORD
    @GetMapping(value = "/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam(name = "email") String email) {
        try {
            String otp = forgotPassService.generateOtp(email);

            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.OK,
                    "OTP has been sent to your Email",
                    otp // Trả về mã OTP trong `data`
            );

            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
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
                    HttpStatus.OK,
                    response, // Thông báo thành công
                    null // Không cần dữ liệu bổ sung
            );
            return ResponseEntity.ok(apiResponse);
        } catch (RuntimeException e) {
            // Tạo phản hồi lỗi với thông báo
            ApiResponse<String> apiResponse = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(), // Thông báo lỗi từ service
                    null // Không cần dữ liệu bổ sung
            );
            return ResponseEntity.badRequest().body(apiResponse);
        }
    }

}
