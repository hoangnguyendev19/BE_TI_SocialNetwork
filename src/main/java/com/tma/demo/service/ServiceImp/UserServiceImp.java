package com.tma.demo.service.ServiceImp;

import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.dto.request.UpdateProfileRequest;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        String email = getUserDetails().getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED, "email does not exist"));

        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "password and confirm password does not match");
        }
        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
//      TODO: revoked all tokens

        } else throw new BaseException(HttpStatus.BAD_REQUEST, "wrong password");
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public UserDto updateProfile(UpdateProfileRequest request){
        String email = getUserDetails().getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED, "email does not exist"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPresentAddress(request.getPresentAddress());
        user.setPermanentAddress(request.getPermanentAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user = userRepository.saveAndFlush(user);
        return mapper.map(user, UserDto.class);
    }


    private  UserDetails getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BaseException(HttpStatus.UNAUTHORIZED, "unauthenticated");
        }
        return (UserDetails) authentication.getPrincipal();
    }
}
