package com.tma.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

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
@Component
@RequiredArgsConstructor
public class UserRepository {

    private final JPAQueryFactory query;

    @Transactional
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(query.selectFrom(user)
                .where(user.email.eq(email).and(isDeletePredicate()))
                .fetchOne());
    }

    public boolean existsByEmail(String email) {
        Long total = query.select(user.id.count())
                .from(user)
                .where(user.email.eq(email).and(isDeletePredicate()))
                .fetchOne();
        return !ObjectUtils.isEmpty(total);
    }

    private Predicate isDeletePredicate() {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(user.isDelete.isFalse());
        return predicate;
    }
}
