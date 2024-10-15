package com.tma.demo.repository;

import com.tma.demo.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
    List<Media> findAllByPostId(UUID post_id);

    List<Media> findAllByIdAndPost(UUID[] deleteFiles, String postId);
}
