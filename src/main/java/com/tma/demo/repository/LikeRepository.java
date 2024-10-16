package com.tma.demo.repository;

import com.tma.demo.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    @Query("SELECT COUNT(l.id) FROM Like l WHERE  l.post.id = :postId")
    long getTotalLikes(@Param("postId") UUID postId);
}
