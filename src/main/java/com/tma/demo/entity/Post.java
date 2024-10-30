package com.tma.demo.entity;

import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Post
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
@Table(name = TableName.POST)
public class Post extends BaseTimeEntity {
    @ManyToOne
    private User user;
    private String content;
    @ManyToOne
    private Post parentPost;
    private boolean isDelete;
}
