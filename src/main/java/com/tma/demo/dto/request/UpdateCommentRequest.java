package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {
    private String comment_id;
    private String user_id;
    private String commentText;
}