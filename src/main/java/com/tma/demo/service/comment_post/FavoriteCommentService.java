package com.tma.demo.service.comment_post;

import com.tma.demo.dto.request.CreateFavoriteCommentRequest;
import com.tma.demo.dto.request.DeleteFavoriteCommentRequest;
import com.tma.demo.dto.request.ViewListFavoriteCommentRequest;
import com.tma.demo.dto.response.CreateFavoriteCommentResponse;
import com.tma.demo.dto.response.ViewListFavoriteCommentResponse;
import org.springframework.stereotype.Service;


public interface FavoriteCommentService {
    CreateFavoriteCommentResponse createFavoriteComment(String commentId);
    String deleteFavoriteComment(String likeCommentId);
}
