package com.tma.demo.service;

public interface JwtService {

    public String extractEmail(String token);

    boolean isTokenValid(String jwt, String username);
}
