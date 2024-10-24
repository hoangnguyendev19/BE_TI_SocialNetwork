package com.tma.demo.service.auth;

import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.request.RefreshTokenRequest;
import com.tma.demo.dto.response.TokenDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;


public interface AuthService {
    public TokenDto authenticate(LoginRequest request);

    TokenDto refreshToken(HttpServletRequest request);
}
