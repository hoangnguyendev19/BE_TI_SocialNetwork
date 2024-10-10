package com.tma.demo.service;

import org.springframework.stereotype.Service;

import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.entity.User;
import com.tma.demo.repository.UserRepository;

@Service
public class RegisterService {
    private final UserRepository userRepository;

    public RegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerDTOtoUser(RegisterRequest registerRequest) {
        if (!isValidPassword(registerRequest.getPassword())) { //Check PassWord
            throw new RuntimeException(
                    "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character.");
        }
        if (!isValidPhoneNumber(registerRequest.getPhoneNumber())) { //
            throw new RuntimeException(
                    "Invalid Phone Number.");
        }
        if (!isValidEmail(registerRequest.getEmail())) { //
            throw new RuntimeException(
                    "Email not correct format.");
        }
        if (registerRequest.getFirstName() == null || registerRequest.getFirstName().isEmpty()) {
            throw new RuntimeException("First name cannot be empty.");
        }

        // Validate last name
        if (registerRequest.getLastName() == null || registerRequest.getLastName().isEmpty()) {
            throw new RuntimeException("Last name cannot be empty.");
        }

        // Validate email
        if (registerRequest.getEmail() == null || registerRequest.getEmail().isEmpty()) {
            throw new RuntimeException("Email cannot be empty.");
        }

        // Validate phone number
        if (registerRequest.getPhoneNumber() == null || registerRequest.getPhoneNumber().isEmpty()) {
            throw new RuntimeException("Phone number cannot be empty.");
        }

        // Check if email already exists
        if (isEmailExist(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Check if passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match.");
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

    private boolean isValidPassword(String password) {
        System.out.println("Checking password: " + password);
        return password != null && password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{8,}$");
    }

    private boolean isValidPhoneNumber(String phonenumber) {
        return phonenumber != null && phonenumber.matches(
                "^\\+?(\\d{1,3})?[-.\\s]?(\\d{2,4})[-.\\s]?(\\d{3,4})[-.\\s]?(\\d{3,4})$");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches(
                "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"); 
    }

}
