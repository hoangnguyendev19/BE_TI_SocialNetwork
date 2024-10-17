package com.tma.demo.service.user.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.FolderNameConstant;
import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
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

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = getUserDetails().getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.WRONG_PASSWORD));
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new BaseException(ErrorCode.CONFIRM_PASSWORD_DOES_NOT_MATCH);
        }
        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
//      TODO: revoked all tokens

        } else throw new BaseException(ErrorCode.WRONG_PASSWORD);
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public UserDto updateProfile(UpdateProfileRequest request) {
        String email = getUserDetails().getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
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
    @Transactional(rollbackFor = {SQLException.class, Exception.class, IOException.class})
    public String changeAvatar(MultipartFile imageFile) throws IOException {
        String email = getUserDetails().getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));

        Map data = cloudinaryService.upload(imageFile.getBytes(), FolderNameConstant.AVATAR, user.getId().toString());
        user.setProfilePictureUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString());
        user = userRepository.saveAndFlush(user);
        return user.getProfilePictureUrl();
    }

    @Override
    public UserDto getUser() {
        String email = getUserDetails().getUsername();
        return getUserByEmail(email);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        return mapper.map(user, UserDto.class);
    }

    private UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED);
        }
        return (UserDetails) authentication.getPrincipal();
    }
}
