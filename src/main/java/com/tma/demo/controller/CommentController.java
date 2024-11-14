package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CommentDto;
import com.tma.demo.filter.IdFilter;
import com.tma.demo.service.comment_post.CommentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.EndPointConstant.*;

@RestController
@RequestMapping(value = COMMENTS)
@RequiredArgsConstructor
public class CommentController {
    private final CommentPostService commentPostService;
//Create
    @PostMapping()
    public ResponseEntity<ApiResponse<CommentDto>> createComment(@RequestBody CommentRequest request) {
        CommentDto commentResponse = commentPostService.createComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_COMMENT_SUCCESS.getMessage(), commentResponse));
    }
//Update
    @PutMapping()
    public ResponseEntity<ApiResponse<String>> updateComment(@RequestBody CommentRequest request) {
        String response = commentPostService.updateComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.UPDATE_COMMENT_SUCCESS.getMessage(), response));
}
//Delete
    @DeleteMapping(COMMENT_ID)
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable String commentId) {
        String response = commentPostService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response,null));
    }
//View
    @PostMapping(VIEW_LIST)
    public ResponseEntity<ApiResponse<Page<CommentDto>>> getAllComments(@RequestBody PagingRequest<IdFilter> pagingRequest) {
        Page<CommentDto> comments = this.commentPostService.fetchAllCommentsByPostId(pagingRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.VIEW_COMMENT_SUCCESS.getMessage(), comments));
    }
//Hidden
    @PutMapping(HIDDEN_LIST_COMMENT_POST+COMMENT_ID)
    public ResponseEntity<ApiResponse<String>> hideComment(@PathVariable String commentId){
        commentPostService.hideComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.HIDDEN_COMMENT_SUCCESS.getMessage(), null));
    }
}
