package com.tma.demo.entity;

import com.tma.demo.common.PaymentStatus;
import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * PaymentController
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.PAYMENT)
public class Payment extends BaseTimeEntity {
    @ManyToOne
    private Room room;
    private int totalAmount;
    private PaymentStatus paymentStatus;

}
