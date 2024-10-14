package com.tma.demo.service.jwt.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.JwtDetails;
import com.tma.demo.common.TokenType;
import com.tma.demo.constant.FieldConstant;
import com.tma.demo.exception.BaseException;
import com.tma.demo.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final JwtDetails jwtDetails;

    public SecretKey getKey() {
        byte[] key = Decoders.BASE64.decode(jwtDetails.getSecretKey());
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
            throw new BaseException(ErrorCode.TOKEN_INVALID);
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
    public String generateToken(String email, TokenType tokenType) {
        Map<String, String> claims = Map.of(
                FieldConstant.EMAIL, email
        );
        if (tokenType.equals(TokenType.ACCESS_TOKEN))
            return buildToken(claims, jwtDetails.getAccessTokenExpiration());
        else return buildToken(claims, jwtDetails.getRefreshTokenExpiration());
    }

    private String buildToken(Map<String, String> claims, Long expiration) {
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get(FieldConstant.EMAIL))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }
}
