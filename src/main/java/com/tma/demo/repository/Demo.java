package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.tma.demo.entity.QUser.user;

/**
 * AASc
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@RequiredArgsConstructor
@Service
public class Demo {
    private final JPAQueryFactory jpaQueryFactory;

    public void a() {
        jpaQueryFactory.selectFrom(user)
                .fetch();
    }
}
