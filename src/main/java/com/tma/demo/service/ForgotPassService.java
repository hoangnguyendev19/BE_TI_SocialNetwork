package com.tma.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

import com.tma.demo.entity.Otp;
import com.tma.demo.repository.OtpRepository;
import com.tma.demo.util.EmailUtil;
import com.tma.demo.util.OtpUtil;

import jakarta.mail.MessagingException;

@Service
public class ForgotPassService {
    private final OtpRepository otpRepository;
    private OtpUtil otpUtil;
    private EmailUtil emailUtil;


    public ForgotPassService(OtpRepository otpRepository, OtpUtil otpUtil, EmailUtil emailUtil) {
        this.otpRepository = otpRepository;
        this.otpUtil = otpUtil;
        this.emailUtil = emailUtil;
    }

    public String verifyAccount(String email, String otp) {
        Otp useroOtp  = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        if (useroOtp.getOtp().equals(otp) && Duration.between(useroOtp.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 100)) {

            otpRepository.save(useroOtp);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String generateOtp(String email) {
        Otp userOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        userOtp.setOtp(otp);
        userOtp.setOtpGeneratedTime(LocalDateTime.now());
        otpRepository.save(userOtp);
        return "Email sent... please verify account within 1 minute";
    }

    public String setPassWord(String email, String password) {
        Otp userOtp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));
        userOtp.setPassword(password);
        this.otpRepository.save(userOtp);
        return "Set Password Success";
     }
}
