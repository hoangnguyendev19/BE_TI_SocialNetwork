package com.tma.demo.service.comment_post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CommentDto;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.filter.IdFilter;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.LikeCommentRepository;
import com.tma.demo.repository.PostRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.comment_post.CommentMapper;
import com.tma.demo.service.comment_post.CommentPostService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentPostServiceImp implements CommentPostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Override
    //Create Comment
    public CommentDto createComment(CommentRequest request) {
        User user = userService.getUserDetails();
        Post post = findPostById(request.getPostId());
        Comment parentComment = StringUtils.isBlank(request.getParentCommentId())
                ? null //true
                : findCommentById(request.getParentCommentId()); //false
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentText(request.getCommentText());
        comment.setParentComment(parentComment);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setLastModified(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment, user);
    }

    @Override
    //Update Comment
    public String updateComment(CommentRequest commentRequest) {
        Comment comment = findCommentById(commentRequest.getCommentId());
        User user = userService.getUserDetails();
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        comment.setCommentText(commentRequest.getCommentText());
        comment.setLastModified(LocalDateTime.now());
        commentRepository.save(comment);
        return SuccessMessage.UPDATE_COMMENT_SUCCESS.getMessage();
    }

    @Override
    //Delete Comment
    public String deleteComment(String commentId) {
        Comment comment = findCommentById(commentId);
        User user = userService.getUserDetails();
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        comment.setDelete(true);
        commentRepository.save(comment);
        return SuccessMessage.DELETE_COMMENT_SUCCESS.getMessage();
    }

    @Override
    public Page<CommentDto> fetchAllCommentsByPostId(PagingRequest<IdFilter> pagingRequest) {
        User user = userService.getUserDetails();
        Post post = findPostById(pagingRequest.getFilter().getId());
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<Comment> comments = commentRepository.findVisibleCommentsByPostId(post.getId(), pageable);

        List<CommentDto> responseList = comments
                .stream()
                .map(comment -> commentMapper.toDTO(comment, user))
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, comments.getTotalElements());
    }

    @Override
    //Hidden Comment
    public String hideComment(String commentId) {
        Comment comment = findCommentById(commentId);
        User user = userService.getUserDetails();
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        comment.setHidden(true);
        commentRepository.save(comment);
        return SuccessMessage.HIDDEN_COMMENT_SUCCESS.getMessage();
    }

    private Post findPostById(String postId) {
        return postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));
    }

    @Override
    public Comment findCommentById(String commentId) {
        return commentRepository.findById(UUID.fromString(commentId))
                .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_NOT_EXIST));
    }
}
