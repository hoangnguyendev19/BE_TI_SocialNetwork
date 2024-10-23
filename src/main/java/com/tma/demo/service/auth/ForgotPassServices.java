package com.tma.demo.service.auth;

import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.request.VerifyOTPRequest;
import com.tma.demo.dto.response.VerifyOtpResponse;
import org.springframework.stereotype.Service;


public interface ForgotPassServices {
    String generateOtp(String email);
    VerifyOtpResponse verifyAccount(VerifyOTPRequest verifyOTPRequest);
    String setPassword(SetPasswordRequest setPasswordRequest);
}
