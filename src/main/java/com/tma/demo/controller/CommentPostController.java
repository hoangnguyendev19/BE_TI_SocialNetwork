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
import com.tma.demo.service.user.CommentPostService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/auth")
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentPostService commentPostService;
//
    @PostMapping(value = "/create-comment-post")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentPostService.createComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_COMMENT_SUCCESS.getMessage(), commentResponse));
    }
//
    @PutMapping(value = "/update-comment-post")
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateComment(@RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse updateresponse = commentPostService.updateComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.UPDATE_COMMENT_SUCCESS.getMessage(), updateresponse));
}
    @DeleteMapping("/delete-comment-post")
    public ResponseEntity<ApiResponse<String>> deleteComment(@RequestBody DeleteCommentRequest deleteCommentRequest) {
        String response = commentPostService.deleteComment(deleteCommentRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response,null));
    }
    @GetMapping("/view-list-comment-post")
    public ResponseEntity<List<Comment>> getAllComments(@RequestBody ViewListCommentRequest viewListCommentRequest) {
        List<Comment> comments = this.commentPostService.fetchAllCommentsByPostId(viewListCommentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

}
