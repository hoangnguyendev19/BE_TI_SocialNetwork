package com.tma.demo.service;

import com.tma.demo.dto.request.ChangePasswordRequest;
import org.springframework.stereotype.Service;

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

    void changePassword(String username, ChangePasswordRequest changePasswordRequest);
}
