package com.tma.demo.repository;

import com.tma.demo.entity.Like;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    @Query("SELECT l.id FROM Like l WHERE  l.post.id = :postId")
    List<String> getTotalLikes(@Param("postId") UUID postId);

    @Query("SELECT l FROM Like l WHERE l.post.id = :postId and l.user.id = :userId")
    Optional<Like> getLikeByUserAndPost(@Param("userId") UUID userId,@Param("postId") UUID postId);

    @Query("SELECT l FROM Like l WHERE l.user.id = :userId")
    Page<Like> getLikeByUser(Pageable pageable, @Param("userId") UUID userId);

    @Query("SELECT l.user FROM Like l WHERE l.post.id = :postId")
    Page<User> getUsersByPost(Pageable pageable, @Param("postId") UUID id);
}
