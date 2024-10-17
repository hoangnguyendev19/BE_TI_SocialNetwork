package com.tma.demo.entity;

import com.tma.demo.common.BaseTimeEntity;
import com.tma.demo.common.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

/**
 * Room
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
@Table(name = "room")
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private BoardingHouse boardingHouse;
    private String roomName;
    private Integer roomRate;
    private Integer electricMeterOldNumber;
    private Integer waterMeterOldNumber;
    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;
    private boolean isDelete;
}
