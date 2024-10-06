package com.tma.demo;

import com.tma.demo.entity.Setting;
import com.tma.demo.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

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
public class Application  {
    @Autowired
    private SettingRepository rp;
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
