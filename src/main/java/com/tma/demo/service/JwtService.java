package com.tma.demo.service;

public interface JwtService {

    public String extractEmail(String token);


    boolean isExpired(String jwt);

    String generateToken(String email, String tokenType);
}
