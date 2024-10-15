package com.tma.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewListCommentResponse {
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("comment_id")
    private String commentId;
    @JsonProperty("commentText")
    private String commentText;

}
