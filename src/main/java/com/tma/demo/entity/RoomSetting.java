package com.tma.demo.entity;

import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * RoomSetting
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
@Table(name = TableName.ROOM_SETTING)
public class RoomSetting extends BaseTimeEntity {
    @ManyToOne
    private BoardingHouse boardingHouse;
    private int electricBill;
    private int waterBill;

}
