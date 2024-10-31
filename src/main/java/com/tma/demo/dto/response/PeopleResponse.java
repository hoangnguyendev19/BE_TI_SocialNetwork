package com.tma.demo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeopleResponse {
    private UUID roomId;
    private List<UserReponseRoom> users;
}


