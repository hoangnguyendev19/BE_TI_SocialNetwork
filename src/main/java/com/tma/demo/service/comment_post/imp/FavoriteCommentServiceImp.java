package com.tma.demo.service.comment_post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.request.CreateFavoriteCommentRequest;
import com.tma.demo.dto.request.DeleteFavoriteCommentRequest;
import com.tma.demo.dto.request.ViewListFavoriteCommentRequest;
import com.tma.demo.dto.response.CreateFavoriteCommentResponse;
import com.tma.demo.dto.response.ViewListFavoriteCommentResponse;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.LikeComment;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.LikeCommentRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.comment_post.CommentPostService;
import com.tma.demo.service.comment_post.FavoriteCommentService;
import com.tma.demo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteCommentServiceImp implements FavoriteCommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;
    private final UserService userService;
    private final CommentPostService commentPostService;

    @Override
    public CreateFavoriteCommentResponse createFavoriteComment(CreateFavoriteCommentRequest createFavoriteCommentRequest) {

        User user = userService.getUserDetails();
        Comment comment = commentPostService.findCommentById(createFavoriteCommentRequest.getCommentId());
        LikeComment likeComment = new LikeComment();
        likeComment.setUser(user);
        likeComment.setComment(comment);
        likeComment.setCreatedAt(LocalDateTime.now());
        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);
        return new CreateFavoriteCommentResponse(
                savedLikeComment.getId().toString(),
                user.getId().toString(),
                comment.getId().toString(),
                comment.getPost().getId().toString(),
                savedLikeComment.getCreatedAt().toString()
        );
    }
    @Override
    public ViewListFavoriteCommentResponse getLikedCommentsByUserId(ViewListFavoriteCommentRequest request) {

        Comment comment = commentPostService.findCommentById(request.getCommentId());
        List<LikeComment> likedComments = likeCommentRepository.findByCommentId(UUID.fromString(request.getCommentId()));
        List<ViewListFavoriteCommentResponse.LikedUserDetail> likedUserDetails = likedComments.stream()
                .map(likeComment -> {
                    String userId = likeComment.getUser().getId().toString();
                    String createdAt = likeComment.getCreatedAt().toString();
                    return new ViewListFavoriteCommentResponse.LikedUserDetail(userId, createdAt);
                })
                .collect(Collectors.toList());
        return new ViewListFavoriteCommentResponse(request.getCommentId(), likedUserDetails);
    }
    @Override
    public String deleteFavoriteComment(DeleteFavoriteCommentRequest deleteFavoriteCommentRequest) {
        User user = userService.getUserDetails();
        Comment comment = commentPostService.findCommentById(deleteFavoriteCommentRequest.getCommentId());
        LikeComment likeComment = likeCommentRepository.findByUserAndComment(user, comment)
                .orElseThrow(() -> new BaseException(ErrorCode.LIKE_DOES_NOT_EXIST));
        likeCommentRepository.delete(likeComment);
        return SuccessMessage.DELETE_FAVOURITE_POST_SUCCESS.getMessage();
    }

}
