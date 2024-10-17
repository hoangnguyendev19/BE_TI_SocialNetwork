package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HiddenCommentResponse {
    private String id;
    private String postId;
    private String userId;
    private String content;
    private boolean isHidden;
    private String createdAt;
    private String lastModified;
}
