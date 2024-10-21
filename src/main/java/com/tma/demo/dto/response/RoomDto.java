package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * RoomDto
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
public class RoomDto {
    private String id;
    private String boardingHouseId;
    private String numberOfPeople;
    private int roomRate;
    private String electricityMeterNumber;
    private String waterMeterNumber;
    private LocalDateTime createdDate;
}
