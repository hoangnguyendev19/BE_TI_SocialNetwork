package com.tma.demo.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class VerifyOtpResponse {

    public VerifyOtpResponse(String email) {
        this.email = email;
    }

    private String email;
}
