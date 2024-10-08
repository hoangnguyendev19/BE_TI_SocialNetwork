package com.tma.demo.service.ServiceImp;

import com.tma.demo.dto.request.LoginRequest;
import com.tma.demo.dto.response.TokenDto;
import com.tma.demo.entity.Token;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.TokenRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.AuthService;
import com.tma.demo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST, "email do not exist"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseException(HttpStatus.BAD_REQUEST, "wrong password");
        }
        String accessToken = jwtService.generateToken(user.getEmail(), "ACCESS_TOKEN");
        String refreshToken = jwtService.generateToken(user.getEmail(), "REFRESH_TOKEN");
        Token token = Token.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
        return modelMapper.map(token, TokenDto.class);
    }
}
