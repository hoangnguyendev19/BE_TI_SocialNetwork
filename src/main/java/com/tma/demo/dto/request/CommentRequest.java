package com.tma.demo.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private String user_id;
    private String post_id;
    private String commentText;


}
