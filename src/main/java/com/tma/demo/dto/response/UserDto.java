package com.tma.demo.dto.response;

import com.tma.demo.common.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * UserDto
 * Version 1.0
 * Date: 08/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/10/2024        NGUYEN             create
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private String firstName;
    private String lastName;
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
}
