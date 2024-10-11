package com.tma.demo.controller;

import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * UserController
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @PutMapping(value = "/password")
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "change password successfully", null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(@RequestBody UpdateProfileRequest request) {
        UserDto userDto = userService.updateProfile(request);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                "update profile successfully",
                userDto
        ));
    }

    @PutMapping(value = "/avatar")
    public ResponseEntity<ApiResponse<String>> changeAvatar(@RequestParam("imageFile") MultipartFile imageFile) {
        String imgUrl = userService.changeAvatar(imageFile);
        return ResponseEntity.ok(new ApiResponse<>(200, "change avatar successfully", imgUrl));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getUserDto(){
        UserDto userDto = userService.getUser();
        return ResponseEntity.ok(new ApiResponse<>(200, "get user successfully", userDto));
    }



}
