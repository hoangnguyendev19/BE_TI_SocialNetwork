package com.tma.demo.service.auth.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.IUserRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.auth.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImp implements RegisterService {
    private final IUserRepository iUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
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

    @Override
    public RegisterResponse saveUser(RegisterRequest registerRequest) {
        User user = registerDTOtoUser(registerRequest);
        // Encode password
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User savedUser = this.iUserRepository.save(user);  //save User
        return new RegisterResponse(  //Response Register
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber());
    }

    @Override
    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
