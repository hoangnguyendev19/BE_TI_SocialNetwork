package com.tma.demo.repository;

import com.tma.demo.entity.Comment;
import com.tma.demo.entity.LikeComment;
import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeCommentRepository extends JpaRepository<LikeComment, UUID> {
    Optional<LikeComment> findByUserAndComment(User user, Comment comment);
    @Query("SELECT COUNT(DISTINCT l) FROM LikeComment l WHERE l.comment.id = :commentId")
    long countDistinctLikesByCommentId(@Param("commentId") UUID commentId);
}
