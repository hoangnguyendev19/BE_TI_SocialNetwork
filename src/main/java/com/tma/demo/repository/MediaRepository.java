package com.tma.demo.repository;

import com.tma.demo.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    List<Media> findAllByPostId(UUID post_id);

    @Query("SELECT m FROM Media m WHERE m.id in (:ids) and m.post.id = :postId")
    List<Media> findAllByIdsAndPostId(@Param("ids") List<UUID> deleteFiles, @Param("postId") UUID postId);
}
