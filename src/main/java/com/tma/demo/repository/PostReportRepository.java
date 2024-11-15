package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.PostReport;
import com.tma.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QPostReport.postReport;

/**
 * PostReportRepository
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
public class PostReportRepository {
    private final JPAQueryFactory query;

    public Optional<PostReport> findByUser(User user) {
        return Optional.ofNullable(query.selectFrom(postReport).where(postReport.user.eq(user))
                .fetchOne());
    }

    public Long findTotalReport(UUID postId) {
        return query.select(postReport.id.count())
                .from(postReport)
                .where(postReport.id.eq(postId))
                .fetchOne();
    }
}
