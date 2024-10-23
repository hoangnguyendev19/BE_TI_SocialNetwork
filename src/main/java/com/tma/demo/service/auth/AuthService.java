package com.tma.demo.service.auth;

import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.request.RefreshTokenRequest;
import com.tma.demo.dto.response.TokenDto;
import org.springframework.stereotype.Service;


public interface AuthService {
    public TokenDto authenticate(LoginRequest request);

    TokenDto refreshToken(RefreshTokenRequest request);
}
