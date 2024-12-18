package com.tma.demo.entity;

import com.tma.demo.common.RoomStatus;
import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

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
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.ROOM)
public class Room extends BaseTimeEntity {
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
