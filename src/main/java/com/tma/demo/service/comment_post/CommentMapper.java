package com.tma.demo.service.comment_post;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.dto.response.CommentDto;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.LikeCommentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentMapper {
    private final LikeCommentRepository likeCommentRepository;
    private final CommentRepository commentRepository;

    public CommentDto toDTO(Comment comment, User user) {
        CommentDto commentDTO = new CommentDto();
        commentDTO.setCommentId(comment.getId().toString());
        commentDTO.setPostId(comment.getPost().getId().toString());
        commentDTO.setUserId(comment.getUser().getId().toString());
        if (comment.getParentComment() != null) {
            commentDTO.setParentCommentId(comment.getParentComment().getId().toString());
        } else {
            commentDTO.setParentCommentId(null);
        }
        commentDTO.setCommentText(comment.getCommentText());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setLastModified(comment.getLastModified());
        commentDTO.setLiked(likeCommentRepository.findByUserAndComment(user, comment).isPresent());
        commentDTO.setOwnedPost(comment.getPost().getUser().getId().equals(user.getId()));
        commentDTO.setOwnedComment(comment.getUser().getId().equals(user.getId()));
        commentDTO.setFirstName(comment.getUser().getFirstName());
        commentDTO.setLastName(comment.getUser().getLastName());
        commentDTO.setProfilePictureUrl(comment.getUser().getProfilePictureUrl());
        commentDTO.setTotalLikes(likeCommentRepository.countDistinctLikesByCommentId(comment.getId()));

        List<CommentDto> childComments = (comment.getChildComments() == null
                ? new ArrayList<>() //true
                : comment.getChildComments()).stream() //false
                .map(childComment -> toDTO((Comment) childComment, user))
                .collect(Collectors.toList());
        commentDTO.setChildComments(childComments);

        return commentDTO;
    }
}
