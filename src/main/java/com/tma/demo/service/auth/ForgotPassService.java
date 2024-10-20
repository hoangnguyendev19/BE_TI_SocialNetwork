package com.tma.demo.service.auth;

import java.time.Duration;
import java.time.LocalDateTime;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.request.VerifyOTPRequest;
import com.tma.demo.exception.BaseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.response.VerifyOtpResponse;
import com.tma.demo.entity.Otp;
import com.tma.demo.entity.User;
import com.tma.demo.repository.OtpRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.util.EmailUtil;
import com.tma.demo.util.OtpUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import com.tma.demo.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ForgotPassService {
    private final OtpRepository otpRepository;
    private final OtpUtil otpUtil;
    private final EmailUtil emailUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String generateOtp(String email) {
        // Find User
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        // Generate OTP
        String otp = otpUtil.generateOtp();
        // Save User and OTP
        Otp geotp = Otp.builder()
                .user(user)
                .otp(otp)
                .build();
//        Otp geotp = new Otp();
//        geotp.setOtp(otp);
//        geotp.setUser(user);
        otpRepository.save(geotp);
        try {
            emailUtil.sendOtpEmail(email, otp); // Send OTP to Email
        } catch (MessagingException e) {
            throw new BaseException(ErrorCode.UNABLE_SEND_OTP);
        }
        return SuccessMessage.OTP_SEND.getMessage();
    }
    public VerifyOtpResponse verifyAccount(VerifyOTPRequest verifyOTPRequest) {
        User user = userRepository.findByEmail(verifyOTPRequest.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        Otp geotp = otpRepository.findByUserAndOtp(user, verifyOTPRequest.getOtp())
                .orElseThrow(() -> new BaseException(ErrorCode.OTP_DOES_NOT_EXIST));
        // Expired OTP
        if (geotp.getOtp().equals(verifyOTPRequest.getOtp()) && Duration.between(
                geotp.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 10000)) {
            // UpdateOTP
            otpRepository.save(geotp);
            // Response
            return new VerifyOtpResponse(geotp.getUser().getEmail());
        }
        // OTP Had Expired
        throw new BaseException(ErrorCode.OTP_EXPIRED);
    }

    public String setPassword(SetPasswordRequest setPasswordRequest) {
        String password = setPasswordRequest.getNewPassword();
        String confirmPassword = setPasswordRequest.getConfirmNewPassword();
        // Check PassWord And Confirm PassWord matching
        if (!password.equals(confirmPassword)) {
            throw new BaseException(ErrorCode.MATCH_PASSWORD);
        }
        // Find User By Email
        User user = userRepository.findByEmail(setPasswordRequest.getEmail())
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
        // HashPassWord And Save User into DB
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return SuccessMessage.UPDATE_PASSWORD_SUCCESS.getMessage();
    }


}
