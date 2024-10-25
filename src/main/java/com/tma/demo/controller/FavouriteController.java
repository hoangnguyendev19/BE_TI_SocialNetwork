package com.tma.demo.controller;

import com.tma.demo.common.APIConstant;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.UserResponse;
import com.tma.demo.filter.IdFilter;
import com.tma.demo.service.favourite.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = APIConstant.FAVOURITE)
@RequiredArgsConstructor
public class FavouriteController {
    private final FavouriteService favouriteService;

    @PostMapping
    public ResponseEntity<ApiResponse<LikeDto>> createFavouritePost(@RequestBody LikeDto likeDto) {
        return ResponseEntity.ok(ApiResponse.<LikeDto>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.CREATE_FAVOURITE_POST_SUCCESS.getMessage())
                .data(favouriteService.createFavouritePost(likeDto))
                .build());
    }

    @DeleteMapping(value = APIConstant.ID)
    public ResponseEntity<ApiResponse<String>> deleteFavouritePost( @PathVariable String id) {
        favouriteService.deleteFavouritePost(id);
        return ResponseEntity.ok(ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.DELETE_FAVOURITE_POST_SUCCESS.getMessage())
                .data(null)
                .build());
    }

    @PostMapping(value = APIConstant.VIEW_LIST)
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getFavouritePosts(@RequestBody PagingRequest<IdFilter> pagingRequest) {
        Page<UserResponse> likeResponses = favouriteService.getFavouritePosts(pagingRequest);
        return ResponseEntity.ok(ApiResponse.<Page<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.GET_FAVOURITE_POSTS_SUCCESS.getMessage())
                .data(likeResponses)
                .build());
    }

}
