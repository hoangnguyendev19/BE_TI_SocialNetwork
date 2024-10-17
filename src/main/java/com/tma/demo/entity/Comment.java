package com.tma.demo.entity;

import com.tma.demo.constant.TableName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Comment
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
@Table(name = TableName.COMMENT)
public class Comment extends BaseTimeEntity {
    @ManyToOne
    private Post post;
    @ManyToOne
    private User user;
    @ManyToOne
    private Comment parentComment;
    private String commentText;

    private boolean isHidden;
    private boolean isDelete;

}
