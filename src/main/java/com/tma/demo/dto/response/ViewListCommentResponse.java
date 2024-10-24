package com.tma.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewListCommentResponse {
    private String postId;
    private String commentId;
    private String userId;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private String commentText;
    private boolean hidden;
    private boolean ownedPost;
    private boolean ownedComment;
    private boolean liked;
    private long totalLikes;
    private LocalDateTime createdAt;
    private LocalDateTime lastModified;
    private List<ViewListCommentResponse> childComments;
}

