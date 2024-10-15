package com.tma.demo.repository;

import com.tma.demo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.id = :id and p.isDelete != true")
    Optional<Post> findPostById(@Param("id") UUID id);
}
