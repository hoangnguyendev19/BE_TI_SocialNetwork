package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.CommentRequest;
import com.tma.demo.dto.request.DeleteCommentRequest;
import com.tma.demo.dto.request.UpdateCommentRequest;
import com.tma.demo.dto.request.ViewListCommentRequest;
import com.tma.demo.dto.response.CommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.entity.Comment;
import com.tma.demo.service.comment_post.CommentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/comment")
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentPostService commentPostService;
//Create
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentPostService.createComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_COMMENT_SUCCESS.getMessage(), commentResponse));
    }
//Update
    @PutMapping
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateComment(@RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse updateresponse = commentPostService.updateComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.UPDATE_COMMENT_SUCCESS.getMessage(), updateresponse));
}
//Delete
    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        String response = commentPostService.deleteComment(deleteCommentRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response,null));
    }
//View
    @GetMapping
    public ResponseEntity<ApiResponse<List<ViewListCommentResponse>>> getAllComments(@RequestBody ViewListCommentRequest viewListCommentRequest) {
        List<ViewListCommentResponse> comments = this.commentPostService.fetchAllCommentsByPostId(viewListCommentRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.VIEW_COMMENT_SUCCESS.getMessage(), comments));
    }

}
