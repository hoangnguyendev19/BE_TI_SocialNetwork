package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CreateCommentResponse;
import com.tma.demo.dto.response.HiddenCommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.service.comment_post.CommentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.tma.demo.common.APIConstant.*;

@RestController
@RequestMapping(value = COMMENTS)
@RequiredArgsConstructor
public class CommentPostController {
    private final CommentPostService commentPostService;
//Create
    @PostMapping()
    public ResponseEntity<ApiResponse<CreateCommentResponse>> createComment(@RequestBody CreateCommentRequest request) {
        CreateCommentResponse commentResponse = commentPostService.createComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(),
                SuccessMessage.CREATED_COMMENT_SUCCESS.getMessage(), commentResponse));
    }
//Update
    @PutMapping(value = UPDATE_COMMENT_POST)
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateComment(@RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse updateresponse = commentPostService.updateComment(request);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.UPDATE_COMMENT_SUCCESS.getMessage(), updateresponse));
}
//Delete
    @DeleteMapping(value = COMMENT_ID)
    public ResponseEntity<ApiResponse<String>> deleteComment(@PathVariable("commentId") String commentId) {
        String response = commentPostService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), response,null));
    }
//View
    @GetMapping()
    public ResponseEntity<ApiResponse<Page<ViewListCommentResponse>>> getAllComments(@RequestParam String postId,@RequestBody PagingRequest<?> pagingRequest) {
        Page<ViewListCommentResponse> comments = this.commentPostService.fetchAllCommentsByPostId(postId,pagingRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.VIEW_COMMENT_SUCCESS.getMessage(), comments));
    }
//Hidden
    @PutMapping(value = HIDDEN_LIST_COMMENT_POST)
    public ResponseEntity<ApiResponse<HiddenCommentResponse>> hideComent(@PathVariable("commentId")String commentId ){
        HiddenCommentResponse hiddenCommentResponse = commentPostService.hideComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.HIDDEN_COMMENT_SUCCESS.getMessage(), hiddenCommentResponse));
    }
}
