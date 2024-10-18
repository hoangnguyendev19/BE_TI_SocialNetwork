package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.service.favourite.FavouriteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FvouriteController
 * Version 1.0
 * Date: 18/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = "/api/v1/favourites")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class FavouriteController {
    private final FavouriteService favouriteService;

    @PostMapping()
    public ResponseEntity<ApiResponse<LikeDto>> createFavouritePost(@RequestBody LikeDto likeDto) {
        return ResponseEntity.ok(ApiResponse.<LikeDto>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.CREATE_FAVOURITE_POST_SUCESS.getMessage())
                .data(favouriteService.createFavouritePost(likeDto))
                .build());
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteFavouritePost(@RequestBody LikeDto likeDto) {
        favouriteService.deleteFavouritePost(likeDto);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.DELETE_FAVOURITE_POST_SUCESS.getMessage())
                .data(null)
                .build());
    }
    @PostMapping(value = "/favourite-posts")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getFavouritePosts(@RequestBody PagingRequest pagingRequest) {
        Page<PostDto> favouritePosts =  favouriteService.getFavouritePosts(pagingRequest);
        return ResponseEntity.ok(ApiResponse.<Page<PostDto>>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.GET_FAVOURITE_POSTS_SUCESS.getMessage())
                .data(favouritePosts)
                .build());
    }

}
