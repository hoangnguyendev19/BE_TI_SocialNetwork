package com.tma.demo.service.comment_post;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.dto.request.CreateFavoriteCommentRequest;
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
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteCommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    public CreateFavoriteCommentResponse createFavoriteComment(CreateFavoriteCommentRequest createFavoriteCommentRequest) {

        User user = userRepository.findById(UUID.fromString(createFavoriteCommentRequest.getUserId()))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        Comment comment = commentRepository.findById(UUID.fromString(createFavoriteCommentRequest.getCommentId()))
                .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_NOT_EXIST));
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

    public ViewListFavoriteCommentResponse getLikedCommentsByUserId(ViewListFavoriteCommentRequest request) {

        if (!commentRepository.existsById(UUID.fromString(request.getCommentId()))) {
            throw new BaseException(ErrorCode.COMMENT_NOT_EXIST);
        }
        List<LikeComment> likedComments = likeCommentRepository.findByCommentId(UUID.fromString(request.getCommentId()));

        List<ViewListFavoriteCommentResponse.LikedUserDetail> likedUserDetails = likedComments.stream()
                .map(likeComment -> {
                    String userId = likeComment.getUser().getId().toString();
                    String userName = likeComment.getUser().getFirstName();
                    String createdAt = likeComment.getCreatedAt().toString();

                    return new ViewListFavoriteCommentResponse.LikedUserDetail(userId, userName, createdAt);
                })
                .collect(Collectors.toList());

        return new ViewListFavoriteCommentResponse(request.getCommentId(), likedUserDetails);
    }
}
