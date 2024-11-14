package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LikeCommentResponse {
    private String id;
    private String userId;
    private String commentId;
    private String postId;
    private String createdAt;
}
