package com.tma.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * UpdateProfileRequest
 * Version 1.0
 * Date: 08/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 08/10/2024        NGUYEN             create
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;
    @NotBlank(message = "date of birth is required")
    private String dateOfBirth;
    @NotBlank(message = "present address is required")
    private String presentAddress;
    @NotBlank(message = "permanent address is required")
    private String permanentAddress;
    @NotBlank(message = "phone number is required")
    private String phoneNumber;
    @NotBlank(message = "city is required")
    private String city;
    @NotBlank(message = "country is required")
    private String country;
}
