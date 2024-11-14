package com.tma.demo.service.comment_post;

import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CommentDto;
import com.tma.demo.entity.Comment;
import com.tma.demo.filter.IdFilter;
import org.springframework.data.domain.Page;


public interface CommentPostService {
    CommentDto createComment(CommentRequest request);
    String updateComment(CommentRequest commentRequest);
    String deleteComment(String commentId);
    Page<CommentDto> fetchAllCommentsByPostId(PagingRequest<IdFilter> pagingRequest);
    String hideComment(String commentId);
    Comment findCommentById(String commentId);
}
