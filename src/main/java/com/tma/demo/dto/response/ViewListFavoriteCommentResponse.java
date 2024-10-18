package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ViewListFavoriteCommentResponse {
    private String commentId;
    private List<LikedUserDetail> likedUsers;
    @Data
    @AllArgsConstructor
    public static class LikedUserDetail {
        private String userId;
        private String userName;
        private String createdAt;
    }
}
