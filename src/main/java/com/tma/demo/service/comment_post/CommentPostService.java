package com.tma.demo.service.comment_post;

import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CreateCommentResponse;
import com.tma.demo.dto.response.HiddenCommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CommentPostService {
    CreateCommentResponse createComment(CreateCommentRequest request);
    UpdateCommentResponse updateComment(UpdateCommentRequest updateCommentRequest);
    String deleteComment(DeleteCommentRequest deleteCommentRequest);
    List<ViewListCommentResponse> fetchAllCommentsByPostId(ViewListCommentRequest viewListCommentRequest);
    HiddenCommentResponse hideComment(HiddenCommentRequest hiddenCommentRequest);
    Comment findCommentById(String commentId);
}
