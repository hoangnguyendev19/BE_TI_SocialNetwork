package com.tma.demo;


import com.tma.demo.common.UserStatus;
import com.tma.demo.entity.User;
import com.tma.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Application
 * Version 1.0
 * Date: 04/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 04/10/2024        NGUYEN             create
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application
//        implements CommandLineRunner
{
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//        User user = User.builder()
//                .email("john.doe@example.com")
//                .password(passwordEncoder.encode("securePassword123@"))
//                .firstName("John")
//                .lastName("Doe")
//                .status(UserStatus.ACTIVE)
//                .dateOfBirth(Date.valueOf(LocalDate.now()))
//                .presentAddress("123 Present St, New York")
//                .permanentAddress("456 Permanent St, New York")
//                .phoneNumber("+1234567890")
//                .city("New York")
//                .country("USA")
//                .state("NY")
//                .profilePictureUrl("https://example.com/profile.jpg")
//                .coverPictureUrl("https://example.com/cover.jpg")
//                .isDelete(false)
//                .lastLogin(LocalDateTime.now())
//                .build();
//        userRepository.save(user);
//    }
}
