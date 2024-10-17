package com.tma.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HiddenCommentRequest {
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("comment_id")
    private String commentId;
}
