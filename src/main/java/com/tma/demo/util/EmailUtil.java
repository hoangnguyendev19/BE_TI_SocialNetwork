package com.tma.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");
        // Sử dụng Thymeleaf để tạo nội dung email
        Context context = new Context();
        context.setVariable("otp", otp); // Đặt biến OTP cho mẫu

        String emailContent = templateEngine.process("otp-email", context); // Tạo nội dung email từ mẫu

        mimeMessageHelper.setText(emailContent, true); // Đặt nội dung email

        javaMailSender.send(mimeMessage);
    }
}
