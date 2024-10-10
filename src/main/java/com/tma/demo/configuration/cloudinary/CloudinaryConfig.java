package com.tma.demo.configuration.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CloudinaryConfig
 * Version 1.0
 * Date: 10/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 10/10/2024        NGUYEN             create
 */
@Configuration
public class CloudinaryConfig {
    @Value("${application.cloudinary.cloud-name}")
    private String cloudName;

    @Value("${application.cloudinary.api-key}")
    private String apiKey;

    @Value("${application.cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }
}
