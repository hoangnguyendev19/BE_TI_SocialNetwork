package com.tma.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HiddenCommentRequest {
    @JsonProperty("comment_id")
    private String commentId;
}
