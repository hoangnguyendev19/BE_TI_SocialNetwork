package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.constant.FolderNameConstant;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.service.auth.ForgotPassService;
import com.tma.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.tma.demo.common.APIConstant.*;

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
@RequestMapping(value = USERS)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ForgotPassService forgotPassService;
    @PutMapping(value = USER_PASSWORD)
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                SuccessMessage.CHANGE_PASSWORD_SUCCESS.getMessage(),
                null));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UserDto>> updateProfile(@RequestBody UpdateProfileRequest request) {
        UserDto userDto = userService.updateProfile(request);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                SuccessMessage.UPDATE_PROFILE_SUCCESS.getMessage(),
                userDto
        ));
    }

    @PutMapping(value = USER_AVATAR)
    public ResponseEntity<ApiResponse<String>> changeAvatar(@RequestParam("imageFile") MultipartFile imageFile)  {
        String imgUrl = userService.changePicture(imageFile, FolderNameConstant.AVATAR);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                SuccessMessage.CHANGE_AVATAR_SUCCESS.getMessage(),
                imgUrl));
    }
    @PutMapping(value = USER_COVER_PICTURE)
    public ResponseEntity<ApiResponse<String>> changeCoverPicture(@RequestParam("imageFile") MultipartFile imageFile)  {
        String imgUrl = userService.changePicture(imageFile, FolderNameConstant.COVER);
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                SuccessMessage.UPDATE_SUCCESS.getMessage(),
                imgUrl));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<UserDto>> getUserDto() {
        UserDto userDto = userService.getUser();
        return ResponseEntity.ok(new ApiResponse<>(
                HttpStatus.OK.value(),
                SuccessMessage.GET_USER_SUCCESS.getMessage(),
                userDto));
    }
    


}
