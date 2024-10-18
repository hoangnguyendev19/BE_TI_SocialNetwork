package com.tma.demo.dto;

import com.tma.demo.entity.User;
import lombok.Data;

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
public class BoardingHouseDto {
    private String id;
    private String boardingHouseName;
    private String presentAddress;
    private String ward;
    private String city;
}
