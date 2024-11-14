package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.response.LikeCommentResponse;
import com.tma.demo.service.comment_post.FavoriteCommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.EndPointConstant.*;


@AllArgsConstructor
@RequestMapping(value = LIKE_COMMENTS)
@RestController
public class LikeCommentController {
    private final FavoriteCommentService favoriteCommentService;
    @PostMapping(COMMENT_ID)
    public ResponseEntity<ApiResponse<LikeCommentResponse>> createFavoriteComment (@PathVariable String commentId) {
        LikeCommentResponse likeCommentResponse = favoriteCommentService.createFavoriteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.CREATE_FAVOURITE_COMMENT.getMessage(), likeCommentResponse));
    }
    @DeleteMapping(LIKE_COMMENT_ID)
    public ResponseEntity<ApiResponse<String>> deleteFavoriteComment(@PathVariable String likeCommentId) {
        String response = favoriteCommentService.deleteFavoriteComment(likeCommentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response, null));
    }
}
