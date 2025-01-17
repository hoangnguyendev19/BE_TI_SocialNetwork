package com.tma.demo.entity;

import com.tma.demo.common.MediaType;
import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Media
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
@Table(name = TableName.MEDIA)
public class Media extends BaseTimeEntity {
    @ManyToOne
    private Post post;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private String mediaUrl;
    private boolean isDelete;

}
