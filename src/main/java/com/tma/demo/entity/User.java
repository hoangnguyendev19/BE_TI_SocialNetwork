package com.tma.demo.entity;

import com.tma.demo.common.BaseTimeEntity;
import com.tma.demo.common.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * User
 * Version 1.0
 * Date: 06/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 06/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    private Date dateOfBirth;
    private String presentAddress;
    private String permanentAddress;
    private String phoneNumber;
    private String city;
    private String country;
    private String state;
    private String profilePictureUrl;
    private String coverPictureUrl;
    private  boolean isDelete;
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }
}
