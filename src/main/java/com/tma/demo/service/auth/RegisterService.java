package com.tma.demo.service.auth;

import com.tma.demo.exception.BaseException;
import org.springframework.stereotype.Service;

import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.entity.User;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.common.ErrorCode;
@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerDTOtoUser(RegisterRequest registerRequest) {
        // Check if email already exists
        if (isEmailExist(registerRequest.getEmail())) {
            throw new BaseException(ErrorCode.EMAIL_EXIST);
        }
        // Check if passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BaseException(ErrorCode.MATCH_PASSWORD);
        }
        // DTO to User
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        return user;
    }
    public RegisterResponse saveUser(User user) {
        User savedUser = this.userRepository.save(user);  //save User
        return new RegisterResponse(  //Response Register
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber());
    }
    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
