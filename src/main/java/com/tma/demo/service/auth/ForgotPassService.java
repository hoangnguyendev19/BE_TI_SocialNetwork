package com.tma.demo.service.auth;

import java.time.Duration;
import java.time.LocalDateTime;

import org.hibernate.mapping.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tma.demo.dto.request.ForgotPasswordRequest;
import com.tma.demo.dto.request.SetPasswordRequest;
import com.tma.demo.dto.response.VerifyOtpResponse;
import com.tma.demo.entity.Otp;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.OtpRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.util.EmailUtil;
import com.tma.demo.util.OtpUtil;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

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
                .orElseThrow(() -> new RuntimeException("Not Found User"));
        // Generate OTP
        String otp = otpUtil.generateOtp();
        // Save User and OTP
        Otp geotp = Otp.builder()
                .user(user)
                .otp(otp)
                .build();
        otpRepository.save(geotp);
        try {
            emailUtil.sendOtpEmail(email, otp); // Send OTP to Email
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again");
        }
        return otp;
    }

    public VerifyOtpResponse verifyAccount(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        Otp geotp = otpRepository.findByUserAndOtp(user, otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP or OTP has expired"));
        System.out.println(geotp.toString());
        // Kiểm tra xem OTP có hợp lệ và chưa hết hạn
        if (geotp.getOtp().equals(otp) && Duration.between(
                geotp.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 100)) {

            // Cập nhật thông tin OTP (nếu cần) và lưu lại
            otpRepository.save(geotp);

            // Trả về phản hồi với trạng thái thành công và thông tin email
            return new VerifyOtpResponse(geotp.getUser().getEmail());
        }

        // Trường hợp OTP không hợp lệ hoặc đã hết hạn
        throw new RuntimeException("Please regenerate OTP and try again");
    }

    public String setPassword(String email, String otp, SetPasswordRequest setPasswordRequest) {
        String password = setPasswordRequest.getNewPassword();
        String confirmPassword = setPasswordRequest.getConfirmNewPassword();
        if (!isValidPassword(password)) {
            throw new RuntimeException(
                    "Password must be at least 8 characters long, contain upper and lower case letters, a number, and a special character.");
        }
        // Check PassWord And Confirm PassWord matching
        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        }
        // Find User By Email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        // Find OTP Of That User
        otpRepository.findByUserAndOtp(user, otp)
                .orElseThrow(() -> new RuntimeException("Invalid OTP"));
        // HashPassWord And Save User into DB
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        return "Password updated successfully.";
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~])[A-Za-z\\d!@#\\$%\\^&\\*\\(\\)_\\+\\[\\]\\{\\};':\"\\\\|,.<>\\/?`~]{8,}$");
    }

}
