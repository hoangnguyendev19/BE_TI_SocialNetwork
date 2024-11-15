package com.tma.demo.filter;

import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

/**
 * RoomFilter
 * Version 1.0
 * Date: 29/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 29/10/2024        NGUYEN             create
 */
@Data
public class RoomFilter {
    private String boardingHouseId;
    private String roomStatus;
    private String paymentStatus;
    private LocalDate date;
}
