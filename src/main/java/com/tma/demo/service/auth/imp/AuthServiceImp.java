package com.tma.demo.service.auth.imp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.TokenType;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.CommonConstant;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.entity.Token;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.TokenRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.auth.AuthService;
import com.tma.demo.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * AuthServiceImp
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
public class AuthServiceImp implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public TokenDto authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorCode.WRONG_PASSWORD);

        }
        user.setLastLogin(LocalDateTime.now());
        user = userRepository.save(user);
        return getTokenDto(user);
    }

    @Override
    @Transactional
    public TokenDto refreshToken(HttpServletRequest request) {
        String authHeader = request.getHeader(AttributeConstant.HEADER_AUTHORIZATION);
        String refreshToken;
        String userEmail = null;
        if (authHeader == null || !authHeader.startsWith(CommonConstant.PREFIX_TOKEN)) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        refreshToken = authHeader.substring(7);
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new BaseException(ErrorCode.TOKEN_INVALID));
        User user = token.getUser();
        return getTokenDto(user);

    }

    private TokenDto getTokenDto(User user) {
        String accessToken = jwtService.generateToken(user.getEmail(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user.getEmail(), TokenType.REFRESH_TOKEN);
        Token token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


}
