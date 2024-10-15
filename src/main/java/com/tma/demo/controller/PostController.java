package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.service.post.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * PostController
 * Version 1.0
 * Date: 11/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = "/api/v1/posts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(
            @RequestParam(value = "files") MultipartFile[] mediaFiles,
            @RequestParam(value = "content") String content) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PostDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(SuccessMessage.CREATED_POST_SUCCESS.getMessage())
                        .data(postService.createPost(content, mediaFiles))
                        .build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PostDto>> updatePost() {
        return null;
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.DELETE_POST_SUCCESS.getMessage())
                .data(null)
                .build());
    }

}
