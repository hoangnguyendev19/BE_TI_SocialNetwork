package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReponseRoom {
    private String phoneNumber;
    private String email;
    private boolean isDelete;
}
