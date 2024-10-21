package com.tma.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SettingBoardingHouseDto
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettingBoardingHouseDto {
    private String boardingHouseId;
    private int waterBill;
    private int electricityBill;
}
