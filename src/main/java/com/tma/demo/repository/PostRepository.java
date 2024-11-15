package com.tma.demo.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QPost.post;
import static com.tma.demo.entity.QRoom.room;

/**
 * PostRepository
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
public class PostRepository {
    private final JPAQueryFactory query;


    public Optional<Post> findPostById(UUID id) {
        return Optional.ofNullable(query.selectFrom(post)
                .where(post.id.eq(id).and(post.isDelete.isFalse()))
                .fetchOne());
    }

    public Page<Post> getNews(Pageable pageable) {
        OrderSpecifier<?> orderSpecifier = getSortOrder(pageable);
        List<Post> results = query
                .selectFrom(post)
                .where(post.isDelete.isFalse())
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = getTotal();
        return new PageImpl<>(results, pageable, total);
    }

    private long getTotal() {
        long total = query.select(post.id.count())
                .from(post)
                .where(post.isDelete.isFalse())
                .fetchOne();
        return total;
    }

    public long getTotalShares( UUID id) {
        return query.select(post.id.count())
                .from(post)
                .where(post.parentPost.id.eq(id).and(post.isDelete.isFalse()))
                .fetchOne();
    }

    public Page<User> getUsersSharedByPost(Pageable pageable, UUID id) {
        OrderSpecifier<?> orderSpecifier = getSortOrder(pageable);
        List<User> results = query
                .select(post.user)
                .from(post)
                .where(post.parentPost.id.eq(id).and(post.isDelete.isFalse()))
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = getTotal();
        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?> getSortOrder(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return null;
        }
        return pageable.getSort().stream()
                .map(order -> {
                    return switch (order.getProperty()) {
                        case "createdAt" -> order.isAscending() ? post.createdAt.asc() : post.createdAt.desc();
                        case "isDelete" -> order.isAscending() ? room.isDelete.asc() : room.isDelete.desc();
                        default -> null;
                    };
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
