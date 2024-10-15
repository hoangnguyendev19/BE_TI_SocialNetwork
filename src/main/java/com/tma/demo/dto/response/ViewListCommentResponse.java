package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewListCommentResponse {
    private String post_id;
    private String comment_id;
    private String commentText;

}
