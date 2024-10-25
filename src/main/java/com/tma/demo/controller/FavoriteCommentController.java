package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.response.CreateFavoriteCommentResponse;
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
    @PostMapping(COMMENT_ID)
    public ResponseEntity<ApiResponse<CreateFavoriteCommentResponse>> createFavoriteComment (@PathVariable("commentId") String commentId) {
        CreateFavoriteCommentResponse createFavoriteCommentResponse = favoriteCommentService.createFavoriteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.CREATE_FAVOURITE_COMMENT.getMessage(),createFavoriteCommentResponse));
    }
    @DeleteMapping(LIKE_COMMENT_ID)
    public ResponseEntity<ApiResponse<String>> deleteFavoriteComment(@PathVariable("CommentId") String CommentId) {
        String response = favoriteCommentService.deleteFavoriteComment(CommentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response, null));
    }
}
