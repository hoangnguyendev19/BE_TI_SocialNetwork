package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePeopleResponse {
    private UUID roomUserid;
    private String fullName;
    private String phoneNumber;
    private boolean isDelete;

}
