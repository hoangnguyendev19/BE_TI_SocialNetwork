package com.tma.demo.service.comment_post;

import com.tma.demo.dto.response.CreateFavoriteCommentResponse;

public interface FavoriteCommentService {
    CreateFavoriteCommentResponse createFavoriteComment(String commentId);
    String deleteFavoriteComment(String CommentId);
}
