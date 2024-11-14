package com.tma.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BoardingHouseDto
 * Version 1.0
 * Date: 18/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/10/2024        NGUYEN             create
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardingHouseDto {
    private String id;
    private String boardingHouseName;
    private String presentAddress;
    private String ward;
    private String city;
    SettingBoardingHouseDto setting;
}
