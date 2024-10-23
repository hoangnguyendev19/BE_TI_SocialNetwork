package com.tma.demo.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PostDto
 * Version 1.0
 * Date: 11/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private String id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String content;
    private long totalShares;
    private long totalLikes;
    private long totalComments;
    private PostDto parentPost;
    private List<MediaDto> mediaList;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
}
