package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String commentId;
    private String postId;
    private String parentCommentId;
    private String userId;
    private String commentText;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private boolean liked;
    private boolean isHidden;
    private boolean isDelete;
    private boolean ownedPost;
    private boolean ownedComment;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private long totalLikes;
    private List<CommentDto> childComments;
}
