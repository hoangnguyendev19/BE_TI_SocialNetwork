package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListPeopleContext {
    private String phoneNumber;
    private String fullName;
}
