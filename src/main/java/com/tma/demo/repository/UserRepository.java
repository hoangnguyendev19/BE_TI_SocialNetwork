package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.constant.TableName;
import com.tma.demo.entity.QUser;
import com.tma.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QUser.user;


/**
 * UserRepository
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

public class UserRepository {

    private final JPAQueryFactory query;

    @Transactional
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(user).where(user.email.eq(email).and(user.isDelete.isFalse())).fetchOne());
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(query.selectFrom(user).where(user.id.eq(id).and(user.isDelete.isFalse())).fetchOne());
    }

    public boolean existsByEmail(String email) {
        return query.selectFrom(user).where(user.email.eq(email).and(user.isDelete.isFalse())).stream().findAny().isPresent();
    }
}
