package com.tma.demo.service.user;

import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

/**
 * UserServiceImp
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */

@Service
public interface UserService {

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    void changePassword(ChangePasswordRequest changePasswordRequest);

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    UserDto updateProfile(UpdateProfileRequest request);

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    String changeAvatar(MultipartFile imageFile);

    UserDto getUser();

    UserDto getUserByEmail(String email);
}
