package com.tma.demo.service.comment_post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.response.LikeCommentResponse;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.LikeComment;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.IUserRepository;
import com.tma.demo.repository.LikeCommentRepository;
import com.tma.demo.service.comment_post.CommentPostService;
import com.tma.demo.service.comment_post.FavoriteCommentService;
import com.tma.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FavoriteCommentServiceImp implements FavoriteCommentService {
    private final IUserRepository iUserRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final UserService userService;
    private final CommentPostService commentPostService;

    @Override
    public LikeCommentResponse createFavoriteComment(String commentId) {

        User user = userService.getUserDetails();
        Comment comment = commentPostService.findCommentById(commentId);
        Optional<LikeComment> existingLike = likeCommentRepository.findByUserAndComment(user, comment);
        if (existingLike.isPresent()) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        LikeComment likeComment = new LikeComment();
        likeComment.setUser(user);
        likeComment.setComment(comment);
        likeComment.setCreatedAt(LocalDateTime.now());
        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);
        return new LikeCommentResponse(
                savedLikeComment.getId().toString(),
                user.getId().toString(),
                comment.getId().toString(),
                comment.getPost().getId().toString(),
                savedLikeComment.getCreatedAt().toString()
        );
    }
    @Override
    public String deleteFavoriteComment(String likeCommentId) {
        User user = userService.getUserDetails();
        LikeComment likeComment = likeCommentRepository.findById(UUID.fromString(likeCommentId))
                .orElseThrow(() -> new BaseException(ErrorCode.LIKE_DOES_NOT_EXIST));

        if (!likeComment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        likeCommentRepository.delete(likeComment);
        return SuccessMessage.DELETE_FAVOURITE_POST_SUCCESS.getMessage();
    }
}
