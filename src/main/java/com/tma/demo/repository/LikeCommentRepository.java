package com.tma.demo.repository;

import com.tma.demo.entity.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeCommentRepository extends JpaRepository<LikeComment, UUID> {
    List<LikeComment> findByCommentId(UUID commentId);
}
