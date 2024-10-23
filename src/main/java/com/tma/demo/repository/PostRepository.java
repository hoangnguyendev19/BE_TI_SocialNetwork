package com.tma.demo.repository;

import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.id = :id and p.isDelete != true")
    Optional<Post> findPostById(@Param("id") UUID id);

    @Query("SELECT p FROM Post p WHERE p.isDelete != true")
    Page<Post> getNews(Pageable pageable);

    @Query("SELECT COUNT(p.id) FROM Post p WHERE p.parentPost.id = :id and p.isDelete != true")
    long getTotalShares(@Param("id") UUID id);

    @Query("SELECT p.user FROM Post p WHERE p.parentPost.id = :postId and p.isDelete != true")
    Page<User> getUsersSharedByPost(Pageable pageable,@Param("postId") UUID uuid);
}
