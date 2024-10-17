package com.tma.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("parent_comment_id")
    private String parentCommentId;
    @JsonProperty("commentText")
    private String commentText;
}
