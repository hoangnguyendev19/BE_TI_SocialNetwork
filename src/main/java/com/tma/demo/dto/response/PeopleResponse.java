package com.tma.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPeopleResponse {
    private UUID roomId;
    private List<UserResponse> users;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private String email;
        private String phoneNumber;
    }
}


