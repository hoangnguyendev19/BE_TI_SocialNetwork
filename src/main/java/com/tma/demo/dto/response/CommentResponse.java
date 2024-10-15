package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String id;
    private String postId;
    private String userId;
    private String content;
    private String createdAt;
}
