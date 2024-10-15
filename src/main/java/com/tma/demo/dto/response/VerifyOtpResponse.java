package com.tma.demo.dto.response;

import lombok.Data;

@Data

public class VerifyOtpResponse {

    public VerifyOtpResponse(String email) {
        this.email = email;
    }

    private String email;
}
