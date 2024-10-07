package com.tma.demo.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapperConfig
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024        NGUYEN             create
 */
@Configuration
public class ModelMapperConfig {
    @Bean
    ModelMapper modelMaper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

        return modelMapper;
    }
}
