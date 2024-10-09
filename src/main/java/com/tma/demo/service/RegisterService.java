package com.tma.demo.service;

import org.springframework.stereotype.Service;

import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.entity.User;
import com.tma.demo.repository.UserRepository;
@Service
public class RegisterService {
     private final UserRepository userRepository;
     
    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerDTOtoUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        return user;
    }
    public User SaveUser(User user) {
        return this.userRepository.save(user);
    }
    public boolean isEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }
        
}
