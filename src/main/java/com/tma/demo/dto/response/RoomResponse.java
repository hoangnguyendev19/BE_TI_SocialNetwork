package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * RoomResponse
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
@Builder
public class RoomResponse {
    private String id;
    private String boardingHouseId;
    private String roomName;
    private Integer roomRate;
    private Integer numberOfPeople;
    private String roomStatus;
    private Integer electricityMeterOldNumber;
    private Integer waterMeterOldNumber;
    private PaymentResponse payment;
    private LocalDateTime createdAt;

}
