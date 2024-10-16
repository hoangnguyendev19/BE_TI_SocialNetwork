package com.tma.demo.service.comment_post;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.request.CommentRequest;
import com.tma.demo.dto.request.DeleteCommentRequest;
import com.tma.demo.dto.request.UpdateCommentRequest;
import com.tma.demo.dto.request.ViewListCommentRequest;
import com.tma.demo.dto.response.CommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
import com.tma.demo.dto.response.ViewListCommentResponse;
import com.tma.demo.entity.Comment;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.PostRepository;
import com.tma.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentPostService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CommentRequest request) {
        // Find user by ID
        User user = userRepository.findById(UUID.fromString(request.getUserId()))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        // Find post by ID
        Post post = postRepository.findById(UUID.fromString(request.getPostId()))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(UUID.fromString(request.getParentCommentId()))
                    .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_NOT_EXIST));
        }
        // Create a new comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentText(request.getCommentText());
        comment.setParentComment(parentComment);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setLastModified(LocalDateTime.now());
        Comment savedComment = commentRepository.save(comment);
        // Create response
        return new CommentResponse(
                savedComment.getId().toString(),
                post.getId().toString(),
                user.getId().toString(),
                savedComment.getCommentText(),
                savedComment.getCreatedAt().toString(),
                savedComment.getLastModified().toString()
        );
    }

    public UpdateCommentResponse updateComment(UpdateCommentRequest updateCommentRequest) {
        //Find Cmt Id
        Comment comment = commentRepository.findById(UUID.fromString(updateCommentRequest.getCommentId()))
                .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_NOT_EXIST));
        //Check User Comment
        if (!comment.getUser().getId().equals(UUID.fromString(updateCommentRequest.getUserId()))) {
            throw new BaseException(ErrorCode.UPDATE_COMMENT_ERROR);
        }
        comment.setCommentText(updateCommentRequest.getCommentText());
        comment.setLastModified(LocalDateTime.now());
        Comment saveComment = commentRepository.save(comment);
        return new UpdateCommentResponse(
                saveComment.getCommentText()
        );
    }

    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        Comment comment = commentRepository.findById(UUID.fromString(deleteCommentRequest.getCommentId()))
                .orElseThrow(() -> new BaseException(ErrorCode.COMMENT_NOT_EXIST));
        if (!comment.getUser().getId().equals(UUID.fromString(deleteCommentRequest.getUserId()))) {
            throw new BaseException(ErrorCode.DELETE_COMMENT_ERROR);
        }
        commentRepository.delete(comment);
        return SuccessMessage.DELETE_COMMENT_SUCCESS.getMessage();
    }

    public List<ViewListCommentResponse> fetchAllCommentsByPostId(ViewListCommentRequest viewListCommentRequest) {
        Post post = postRepository.findById(UUID.fromString(viewListCommentRequest.getPostId()))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));
        List<Comment> parentComments = commentRepository.findByPostIdAndParentCommentIsNull(post.getId());

        List<ViewListCommentResponse> responseList = new ArrayList<>();
//      Loop Parent Comments -> Response
        for (Comment comment : parentComments) {
            responseList.add(convertToResponse(comment));
        }
        return responseList;
    }
// Response
    private ViewListCommentResponse convertToResponse(Comment comment) {
        ViewListCommentResponse response = new ViewListCommentResponse();
        response.setCommentId(comment.getId().toString());
        response.setPostId(comment.getPost().getId().toString());
        response.setUserId(comment.getUser().getId().toString());
        response.setCommentText(comment.getCommentText());
        response.setCreatedAt(comment.getCreatedAt());
        response.setLastModified(comment.getLastModified());
        List<ViewListCommentResponse> childComments = new ArrayList<>();
        List<Comment> childCommentsEntities = commentRepository.findByParentCommentId(comment.getId());
        for (Comment childComment : childCommentsEntities) {
            childComments.add(convertToResponse(childComment));
        }
        response.setChildComments(childComments);
        return response;
    }
}
