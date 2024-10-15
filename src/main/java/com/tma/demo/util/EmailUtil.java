package com.tma.demo.util;

import com.tma.demo.common.SubjectOTP;
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
        mimeMessageHelper.setSubject(SubjectOTP.OTP_SUBJECT.getMessage());
        Context context = new Context();
        context.setVariable("otp", otp);
        mimeMessageHelper.setText(templateEngine.process("otp-email", context), true);
        javaMailSender.send(mimeMessage);
    }
}
