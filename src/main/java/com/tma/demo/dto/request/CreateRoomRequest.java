package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CreateRoomRequest
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@Data
public class CreateRoomRequest{
    private String boardingHouseId;
    private String roomName;
    private String roomStatus;
    private int electricityMeterOldNumber;
    private int waterMeterOldNumber;
}
