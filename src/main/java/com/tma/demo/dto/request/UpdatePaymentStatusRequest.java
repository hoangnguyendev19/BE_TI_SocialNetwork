package com.tma.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UpdatePaymentStatusRequest
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentStatusRequest {
    private String id;
    private String status;
}
