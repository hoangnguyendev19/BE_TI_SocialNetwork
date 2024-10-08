package com.tma.demo.controller;

import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.service.AuthService;
import com.tma.demo.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(
            @RequestBody @Valid LoginRequest request){
        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK," login successfully", authService.authenticate(request)));
    }
}
