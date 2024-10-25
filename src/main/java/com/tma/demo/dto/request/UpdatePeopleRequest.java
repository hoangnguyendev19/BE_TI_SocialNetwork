package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePeopleRequest {
    private String roomId;
    private List<PeopleRequest> people;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeopleRequest {
        private String email;
        private String phoneNumber;
    }
}
