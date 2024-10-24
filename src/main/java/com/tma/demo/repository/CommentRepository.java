package com.tma.demo.repository;

import com.tma.demo.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByPostId(UUID postId);

    @Query("SELECT count (c.id) FROM Comment c WHERE c.post.id = :postId and c.isDelete != true ")
    long getTotalComments(@Param("postId") UUID postId);
    Page<Comment> findByPostIdAndParentCommentIsNull(UUID postId, Pageable pageable);
    List<Comment> findByParentCommentId(UUID parentCommentId);
}
