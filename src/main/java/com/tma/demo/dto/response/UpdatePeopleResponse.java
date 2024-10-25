package com.tma.demo.dto.response;

import com.tma.demo.dto.request.UpdatePeopleRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePeopleResponse {
    private UUID roomId;
    private List<PeopleResponse> people;
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PeopleResponse {
        private String email;
        private String phoneNumber;
    }
}
