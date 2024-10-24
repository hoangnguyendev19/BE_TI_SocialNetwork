package com.tma.demo.service.comment_post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.CreateCommentResponse;
import com.tma.demo.dto.response.HiddenCommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.LikeCommentRepository;
import com.tma.demo.repository.PostRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.comment_post.CommentPostService;
import com.tma.demo.service.user.UserService;
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
    private final UserRepository userRepository;
    private final LikeCommentRepository likeCommentRepository;
    @Override
    //Create Comment
    public CreateCommentResponse createComment(CreateCommentRequest request) {
        User user = userService.getUserDetails();
        Post post = findPostById(request.getPostId());
        Comment parentComment = StringUtils.isBlank(request.getParentCommentId())
                ? null
                : findCommentById(request.getParentCommentId());
        // Create a new comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentText(request.getCommentText());
        comment.setParentComment(parentComment);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setLastModified(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        String parentCommentString = parentComment != null
                ? parentComment.getId().toString()
                : "";
        // Create response
        return new CreateCommentResponse(
                savedComment.getId().toString(),
                post.getId().toString(),
                user.getId().toString(),
                parentCommentString,
                savedComment.getCommentText(),
                savedComment.getCreatedAt().toString(),
                savedComment.getLastModified().toString()
        );
    }
    @Override
    //Update Comment
    public UpdateCommentResponse updateComment(UpdateCommentRequest updateCommentRequest) {
        //Find Cmt Id
        Comment comment = findCommentById(updateCommentRequest.getId());
        User user = userService.getUserDetails();
        //Check User
        if (!comment.getUser().getId().equals(user.getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        comment.setCommentText(updateCommentRequest.getCommentText());
        comment.setLastModified(LocalDateTime.now());
        Comment saveComment = commentRepository.save(comment);
        return new UpdateCommentResponse(saveComment.getCommentText()
        );
    }
    @Override
    //Delete Comment
    public String deleteComment(String commentId) {
        Comment comment = findCommentById(commentId);
        User user = userService.getUserDetails();
        if (!comment.getUser().getId().equals(user.getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        commentRepository.delete(comment);
        return SuccessMessage.DELETE_COMMENT_SUCCESS.getMessage();
    }
    @Override
    public Page<ViewListCommentResponse> fetchAllCommentsByPostId(PagingRequest<CommentFilter> pagingRequest) {
        User user = userService.getUserDetails();
        CommentFilter filter = pagingRequest.getFilter();
        String postId = filter.getPostId();
        Post post = findPostById(postId);
        Pageable pageable = PageRequest.of(pagingRequest.getPage(), pagingRequest.getSize(),
                Sort.by(pagingRequest.getSortBy(), pagingRequest.getSortField()));

        Page<Comment> comments = commentRepository.findByPostIdAndParentCommentIsNull(post.getId(), pageable);

        List<ViewListCommentResponse> responseList = comments.stream()
                .map(comment -> convertToResponse(comment, user, post))
                .collect(Collectors.toList());

        return new PageImpl<>(responseList, pageable, comments.getTotalElements());
    }

    private ViewListCommentResponse convertToResponse(Comment comment, User user,Post post) {
        ViewListCommentResponse response = new ViewListCommentResponse();
        response.setCommentId(comment.getId().toString());
        response.setPostId(comment.getPost().getId().toString());
        response.setUserId(comment.getUser().getId().toString());
        response.setCommentText(comment.getCommentText());
        response.setCreatedAt(comment.getCreatedAt());
        response.setHidden(comment.isHidden());
        response.setLastModified(comment.getLastModified());
        response.setLiked(likeCommentRepository.findByUserAndComment(user, comment).isPresent());
        response.setOwnedPost(post.getUser().getId().equals(user.getId()));
        response.setOwnedComment(comment.getUser().getId().equals(user.getId()));
        response.setFirstName(comment.getUser().getFirstName());
        response.setLastName(comment.getUser().getLastName());
        response.setProfilePictureUrl(comment.getUser().getProfilePictureUrl());

        List<ViewListCommentResponse> childComments = commentRepository.findByParentCommentId(comment.getId())
                .stream()
                .map(childComment -> convertToResponse(childComment, user, post))
                .collect(Collectors.toList());

        response.setChildComments(childComments);
        return response;
    }

    @Override
    //Hidden Comment
    public HiddenCommentResponse hideComment(String commentId) {
        Comment comment = findCommentById(commentId);
        User user = userService.getUserDetails();
        //CheckUser
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        comment.setHidden(true);
        commentRepository.save(comment);
        return new HiddenCommentResponse(
                comment.getId().toString(),
                comment.getPost().getId().toString(),
                comment.getUser().getId().toString(),
                comment.getCommentText(),
                comment.isHidden(),
                comment.getCreatedAt().toString(),
                comment.getLastModified().toString()
        );
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
