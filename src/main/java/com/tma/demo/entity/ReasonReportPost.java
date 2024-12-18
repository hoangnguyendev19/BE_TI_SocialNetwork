package com.tma.demo.entity;

import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * ReasonReportPost
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.REASON_REPORT_POST)
public class ReasonReportPost extends BaseTimeEntity {
    private String reason;
}
