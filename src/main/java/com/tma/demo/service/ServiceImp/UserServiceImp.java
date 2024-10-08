package com.tma.demo.service.ServiceImp;

import com.tma.demo.dto.request.ChangePasswordRequest;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.SQL;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public void changePassword(String email, ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(HttpStatus.UNAUTHORIZED, "Invalid email"));

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmNewPassword())){
            throw new BaseException(HttpStatus.BAD_REQUEST,"password and confirm password does not match");
        }
        if(passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
        }
        else throw new BaseException(HttpStatus.BAD_REQUEST, "wrong password");
    }
}
