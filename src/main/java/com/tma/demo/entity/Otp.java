package com.tma.demo.entity;


import com.tma.demo.constant.TableName;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = TableName.OTP)
@EntityListeners(AuditingEntityListener.class)
public class Otp extends BaseTimeEntity {
    @ManyToOne
    private User user;
    private String otp;
    @CreatedDate
    private LocalDateTime otpGeneratedTime;
}
