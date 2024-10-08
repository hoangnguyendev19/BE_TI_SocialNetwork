package com.tma.demo.service;

public interface JwtService {

    public String extractEmail(String token);

    boolean isTokenValid(String jwt, String username);

    boolean isExpired(String jwt);

    String generateToken(String email, String tokenType);
}
