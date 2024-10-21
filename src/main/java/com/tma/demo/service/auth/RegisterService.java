package com.tma.demo.service.auth;

import com.tma.demo.dto.request.RegisterRequest;
import com.tma.demo.dto.response.RegisterResponse;
import com.tma.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface RegisterService {
    User registerDTOtoUser(RegisterRequest registerRequest);
    RegisterResponse saveUser(RegisterRequest registerRequest);
    boolean isEmailExist(String email);
}
