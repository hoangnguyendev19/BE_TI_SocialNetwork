package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * CreatePostRequest
 * Version 1.0
 * Date: 16/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 16/10/2024        NGUYEN             create
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequest {
    private List<String> files;
    private String content;
    private String parentPostId;
}
