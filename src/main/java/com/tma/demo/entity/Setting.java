package com.tma.demo.entity;

import com.tma.demo.common.SettingKey;
import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Settings
 * Version 1.0
 * Date: 06/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 06/10/2024        NGUYEN             create
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.SETTING)
public class Setting extends BaseTimeEntity {
    @Enumerated(EnumType.STRING)
    private SettingKey key;
    private String value;

}
