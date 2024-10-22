package com.tma.demo.service.user.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.MediaType;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.FolderNameConstant;
import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.TokenRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.cloudinary.CloudinaryService;
import com.tma.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

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
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final TokenRepository tokenRepository;

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = getUserDetails();
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new BaseException(ErrorCode.CONFIRM_PASSWORD_DOES_NOT_MATCH);
        }
        if (changePasswordRequest.getNewPassword().equals(changePasswordRequest.getCurrentPassword())) {
            throw new BaseException(ErrorCode.NEW_PASSWORD_EQUALS_CURRENT_PASSWORD);
        }
        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            revokeAllTokens(user.getId());
        } else throw new BaseException(ErrorCode.WRONG_PASSWORD);
    }

    private void revokeAllTokens(UUID id) {
        tokenRepository.deleteAllByUserId(id);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public UserDto updateProfile(UpdateProfileRequest request) {
        User user = getUserDetails();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(Date.valueOf(request.getDateOfBirth()));
        user.setPresentAddress(request.getPresentAddress());
        user.setPermanentAddress(request.getPermanentAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user = userRepository.saveAndFlush(user);
        return mapper.map(user, UserDto.class);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public String changePicture(MultipartFile imageFile, String folder) {
        User user = getUserDetails();
        String url;
        Map data;
        try {
            data = cloudinaryService.upload(imageFile.getBytes(), folder, user.getId().toString());
        } catch (IOException e) {
            throw new BaseException(ErrorCode.IMAGE_UPLOAD_FAILED);
        }
        if (folder.equals(FolderNameConstant.AVATAR)) {
            user.setProfilePictureUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString());
            url = user.getProfilePictureUrl();
        } else {
            user.setCoverPictureUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString());
            url = user.getCoverPictureUrl();
        }
        user = userRepository.saveAndFlush(user);
        return url;
    }

    @Override
    public UserDto getUser() {
        User user = getUserDetails();
        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public User getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED);
        }
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
    }
}
