package com.tma.demo.service.jwt;

import com.tma.demo.common.TokenType;

public interface JwtService {

    public String extractEmail(String token);

    boolean isExpired(String jwt);

    String generateToken(String email, TokenType tokenType);
}
