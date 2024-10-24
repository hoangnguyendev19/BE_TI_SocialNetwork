package com.tma.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPeopleRequest {
    @JsonProperty("room_id")
    private String roomId;
    private List<PeopleRequest> people;
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class PeopleRequest {
        private String email;
        private String name;
    }
}

