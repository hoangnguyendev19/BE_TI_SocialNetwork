package com.tma.demo.dto.request;

import com.tma.demo.constant.ValidateFieldMessage;
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
    @NotBlank(message = ValidateFieldMessage.FIRST_NAME)
    private String firstName;
    @NotBlank(message = ValidateFieldMessage.LAST_NAME)
    private String lastName;
    @NotBlank(message = ValidateFieldMessage.DATE_OF_BIRTH)
    private String dateOfBirth;
    @NotBlank(message = ValidateFieldMessage.PRESENT_ADDRESS)

    private String presentAddress;
    @NotBlank(message = ValidateFieldMessage.PERMANENT_ADDRESS)
    private String permanentAddress;
    @NotBlank(message = ValidateFieldMessage.PHONE_NUMBER)
    private String phoneNumber;
    @NotBlank(message = ValidateFieldMessage.CITY)
    private String city;
    @NotBlank(message = ValidateFieldMessage.COUNTRY)
    private String country;
}
