package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReponseRoom {
    private UUID roomUserId;
    private String fullName;
    private String phoneNumber;
    private boolean isDelete;
}
