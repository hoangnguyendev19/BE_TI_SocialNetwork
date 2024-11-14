package com.tma.demo.service.comment_post;

import com.tma.demo.dto.response.LikeCommentResponse;


public interface FavoriteCommentService {
    LikeCommentResponse createFavoriteComment(String commentId);
    String deleteFavoriteComment(String likeCommentId);
}
