package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.CreateFavoriteCommentRequest;
import com.tma.demo.dto.request.ViewListFavoriteCommentRequest;
import com.tma.demo.dto.response.CreateFavoriteCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.dto.response.ViewListFavoriteCommentResponse;
import com.tma.demo.service.comment_post.FavoriteCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.APIConstant.*;

@AllArgsConstructor
@RequestMapping(value = LIKE_COMMENTS)
@RestController
public class FavoriteCommentController {
    private final FavoriteCommentService favoriteCommentService;
    @PostMapping(value = CREATE_LIKE_COMMENTS)
    public ResponseEntity<ApiResponse<CreateFavoriteCommentResponse>> createFavoriteComment (@RequestBody CreateFavoriteCommentRequest createFavoriteCommentRequest) {
        CreateFavoriteCommentResponse createFavoriteCommentResponse = favoriteCommentService.createFavoriteComment(createFavoriteCommentRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.CREATE_FAVOURITE_COMMENT.getMessage(),createFavoriteCommentResponse));
    }
    @GetMapping(value = VIEW_LIST_LIKE_COMMENTS)
    public ResponseEntity<ApiResponse<ViewListFavoriteCommentResponse>> viewListFavoriteComment (@RequestBody ViewListFavoriteCommentRequest viewListFavoriteCommentRequest) {
        ViewListFavoriteCommentResponse viewListFavoriteCommentResponse = favoriteCommentService.getLikedCommentsByUserId(viewListFavoriteCommentRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.VIEW_FAVOURITE_COMMENT.getMessage(), viewListFavoriteCommentResponse));
    }
}
