package com.tma.demo.service.ServiceImp;

import com.tma.demo.exception.BaseException;
import com.tma.demo.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtServiceImp
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
@Slf4j
public class JwtServiceImp implements JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims extractAllClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException expiredJwtException) {
            claims = expiredJwtException.getClaims();
        } catch (JwtException e) {
            log.error(e.getMessage());
            throw new BaseException(HttpStatus.UNAUTHORIZED, "unauthorized");
        }
        return claims;
    }

    // extract to specific claim
    public <T> T extractClaims(String Token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(Token);
        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaims(token, Claims::getSubject);
    }



    public Date extractExpired(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public boolean isExpired(String token) {
        return extractExpired(token).before(new Date());

    }
    @Override
    public String generateToken(String email, String tokenType) {
        Map<String, String> claims = Map.of(
                "email", email
        );
        if (tokenType.equals("ACCESS_TOKEN"))
            return buildToken(claims, accessTokenExpiration);
        else return buildToken(claims, refreshTokenExpiration);

    }

    private String buildToken(Map<String, String> claims, Long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("email"))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }
}
