package com.tma.demo.repository;

import com.tma.demo.entity.PostReport;
import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * PostReportRepository
 * Version 1.0
 * Date: 16/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 16/10/2024        NGUYEN             create
 */
public interface PostReportRepository extends JpaRepository<PostReport, UUID> {
    Optional<PostReport> findByUser(User user);

    @Query("SELECT COUNT(r) FROM PostReport r WHERE r.post.id = :postId ")
    int findTotalReport(@Param("postId") UUID postId);
}
