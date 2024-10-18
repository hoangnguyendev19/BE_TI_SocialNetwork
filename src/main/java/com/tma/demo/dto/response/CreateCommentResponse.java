package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCommentResponse {
    private String id;
    private String postId;
    private String userId;
    private String parentCommentId;
    private String content;
    private String createdAt;
    private String lastModified;
}
