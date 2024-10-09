package com.tma.demo.controller;

import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.entity.User;
import com.tma.demo.service.AuthService;
import com.tma.demo.service.ForgotPassService;
import com.tma.demo.service.JwtService;
import com.tma.demo.service.RegisterService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> RegisterUser(@Valid @RequestBody RegisterRequest registerRequest)

    {
        User user = this.registerService.registerDTOtoUser(registerRequest);

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        this.registerService.SaveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Register Success");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK, " login successfully", authService.authenticate(request)));
    }

    @PutMapping(value = "/verify-otp")
    public ResponseEntity<String> verifyAccount(@RequestParam String email,
            @RequestParam String otp) {
        return new ResponseEntity<>(forgotPassService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PutMapping(value = "/forgot-password")
    public ResponseEntity<String> forgotpassword(@RequestParam String email) {

        return new ResponseEntity<>(forgotPassService.generateOtp(email), HttpStatus.OK);
    }

    @PutMapping(value = "/set-password")
    public ResponseEntity<String> setpassword(@RequestParam String email, @RequestHeader String password,
            @RequestHeader String confirmPassWord) {
        // String user = userService.setPassWord(email,password);
        // String hassPassword = this.passwordEncoder.encode(user.getPassword());
        // user.setPassword(hassPassword);

        return new ResponseEntity<>(forgotPassService.setPassWord(email, confirmPassWord), HttpStatus.OK);
    }

}
