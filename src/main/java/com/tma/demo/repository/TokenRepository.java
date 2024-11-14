package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QToken.token;

/**
 * TokenRepository
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final JPAQueryFactory query;

    public Optional<Token> findByRefreshToken(String refreshToken) {
        return Optional.ofNullable(query.selectFrom(token).where(token.refreshToken.eq(refreshToken).and(token.isRevoked.isFalse()))
                .fetchOne());
    }

    public Optional<Token> findByAccessToken(String accessToken) {
        return Optional.ofNullable(query.selectFrom(token)
                .where(token.accessToken
                        .eq(accessToken)
                        .and(token.isRevoked.isFalse()))
                .fetchOne());
    }

    public void deleteAllByUserId(UUID id) {
        query.delete(token).where(token.user.id.eq(id)).execute();
    }
}
