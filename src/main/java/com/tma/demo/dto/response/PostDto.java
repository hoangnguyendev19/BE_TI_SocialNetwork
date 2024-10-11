package com.tma.demo.dto.response;

import lombok.*;

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
    private String content;
    private PostDto parentPost;
    private List<MediaDto> mediaList;
}