package com.tma.demo.service.user;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.dto.request.CommentRequest;
import com.tma.demo.dto.request.DeleteCommentRequest;
import com.tma.demo.dto.request.UpdateCommentRequest;
import com.tma.demo.dto.request.ViewListCommentRequest;
import com.tma.demo.dto.response.CommentResponse;
import com.tma.demo.dto.response.UpdateCommentResponse;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentPostService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public CommentResponse createComment(CommentRequest request) {
        // Find user by ID
        User user = userRepository.findById(UUID.fromString(request.getUser_id()))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));

        // Find post by ID
        Post post = postRepository.findById(UUID.fromString(request.getPost_id()))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));

        // Create a new comment
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentText(request.getCommentText());
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        // Create response
        return new CommentResponse(
                savedComment.getId().toString(),
                post.getId().toString(),
                user.getId().toString(),
                savedComment.getCommentText(),
                savedComment.getCreatedAt().toString()
        );
    }
    public UpdateCommentResponse updateComment(UpdateCommentRequest updateCommentRequest) {
        //
        Comment comment = commentRepository.findById(UUID.fromString(updateCommentRequest.getComment_id()))
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        //Check User Comment
        if (!comment.getUser().getId().equals(UUID.fromString(updateCommentRequest.getUser_id()))) {
            throw new RuntimeException("You are not authorized to update this comment.");
        }
        //
        comment.setCommentText(updateCommentRequest.getCommentText());
        comment.setLastModified(LocalDateTime.now());
        //
        Comment saveComment = commentRepository.save(comment);
        return new UpdateCommentResponse(
                saveComment.getCommentText()
        );
    }
    public String deleteComment(DeleteCommentRequest deleteCommentRequest) {
        //
        Comment comment = commentRepository.findById(UUID.fromString(deleteCommentRequest.getComment_id()))
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        //
        if (!comment.getUser().getId().equals(UUID.fromString(deleteCommentRequest.getUser_id()))) {
            throw new RuntimeException("You are not authorized to delete this comment.");
        }

        // Xóa comment
        commentRepository.delete(comment);
        return "delete-success";
    }
    public List<Comment> fetchAllCommentsByPostId(ViewListCommentRequest viewListCommentRequest) {
        return commentRepository.findByPostId(UUID.fromString(viewListCommentRequest.getPost_id()));
    }

}
