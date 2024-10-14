package com.tma.demo.service.auth.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.TokenType;
import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.entity.Token;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.TokenRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.auth.AuthService;
import com.tma.demo.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public TokenDto authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(ErrorCode.WRONG_PASSWORD);
        }
        String accessToken = jwtService.generateToken(user.getEmail(), TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user.getEmail(), TokenType.REFRESH_TOKEN);
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
        user.setLastLogin(LocalDateTime.now());
        user = userRepository.save(user);
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(modelMapper.map(user, UserDto.class))
                .build();
    }
}
