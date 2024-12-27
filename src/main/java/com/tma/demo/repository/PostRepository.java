package com.tma.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.constant.FieldConstant;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QPost.post;
import static com.tma.demo.entity.QRoom.room;
import static com.tma.demo.entity.QUser.user;

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
@Component
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
                .where(isDeletePredicate())
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = getTotal();
        return new PageImpl<>(results, pageable, total);
    }

    private long getTotal() {
        Long total = query.select(post.id.count())
                .from(post)
                .where(isDeletePredicate())
                .fetchOne();
        return ObjectUtils.isEmpty(total) ? 0 : total;
    }

    public long getTotalShares( UUID id) {
        Long total =  query.select(post.id.count())
                .from(post)
                .where(post.parentPost.id.eq(id), isDeletePredicate())
                .fetchOne();
        return ObjectUtils.isEmpty(total) ? 0 : total;
    }

    public Page<User> getUsersSharedByPost(Pageable pageable, UUID id) {
        OrderSpecifier<?> orderSpecifier = getSortOrder(pageable);
        List<User> results = query
                .select(post.user)
                .from(post)
                .where(post.parentPost.id.eq(id), isDeletePredicate())
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
                        case FieldConstant.CREATED_AT -> order.isAscending() ? post.createdAt.asc() : post.createdAt.desc();
                        case FieldConstant.IS_DELETE -> order.isAscending() ? room.isDelete.asc() : room.isDelete.desc();
                        default -> post.lastModified.desc();
                    };
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
    private Predicate isDeletePredicate() {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(post.isDelete.isFalse());
        return predicate;
    }
}
