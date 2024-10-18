package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.CreatePostRequest;
import com.tma.demo.dto.request.UpdatePostRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.repository.PostRepository;
import com.tma.demo.service.favourite.FavouriteService;
import com.tma.demo.service.post.PostService;
import com.tma.demo.service.report.ReportService;
import com.tma.demo.util.PageUtil;
import com.tma.demo.dto.request.PagingRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final PostRepository postRepository;
    private final ReportService reportService;
    private final FavouriteService favouriteService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostDto>> createPost(@RequestBody CreatePostRequest createPostRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PostDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(SuccessMessage.CREATED_POST_SUCCESS.getMessage())
                        .data(postService.createPost(createPostRequest))
                        .build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse<PostDto>> updatePost(@RequestBody UpdatePostRequest updatePostRequest) {
        PostDto postDto = postService.updatePost(updatePostRequest);
        return ResponseEntity.ok(ApiResponse.<PostDto>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_POST_SUCCESS.getMessage())
                .data(postDto)
                .build());
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

    @PostMapping("/news")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getNews(@RequestBody PagingRequest pagingRequest) {
        Page<PostDto> postsDto = postService.getNews(pagingRequest);
        return ResponseEntity.ok(
                ApiResponse.<Page<PostDto>>builder()
                        .code(HttpStatus.OK.value())
                        .message(SuccessMessage.GET_NEWS_SUCCESS.getMessage())
                        .data(postsDto)
                        .build()
        );
    }

    @PostMapping("/favourites")
    public ResponseEntity<ApiResponse<LikeDto>> createFavouritePost(@RequestBody LikeDto likeDto){
        return ResponseEntity.ok(ApiResponse.<LikeDto>builder()
                        .code(HttpStatus.CREATED.value())
                        .message(SuccessMessage.CREATE_FAVOURITE_POST_SUCESS.getMessage())
                        .data(favouriteService.createFavouritePost(likeDto))
                .build());
    }

}
