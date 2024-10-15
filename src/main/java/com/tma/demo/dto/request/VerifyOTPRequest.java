package com.tma.demo.dto.request;
import lombok.Data;

@Data
public class VerifyOTPRequest {
    private String email;
    private String otp;
}
