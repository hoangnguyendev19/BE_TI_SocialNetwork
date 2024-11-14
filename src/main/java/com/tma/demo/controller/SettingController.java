package com.tma.demo.controller;

import com.tma.demo.common.EndPointConstant;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.SettingDto;
import com.tma.demo.service.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * SettingController
 * Version 1.0
 * Date: 16/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 16/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = EndPointConstant.SETTING)
@RequiredArgsConstructor
public class SettingController {
    private final SettingService settingService;
    @PostMapping
    public ResponseEntity<ApiResponse<SettingDto>> createSetting(@RequestBody SettingDto settingRequest) {
       return ResponseEntity.ok().body(ApiResponse.<SettingDto>builder()
                       .code(HttpStatus.OK.value())
                       .message(SuccessMessage.CREATED_SETTING_SUCCESS.getMessage())
                       .data(settingService.createSetting(settingRequest))
               .build());

    }
    @PutMapping
    public ResponseEntity<ApiResponse<SettingDto>> updateSetting(@RequestBody SettingDto settingRequest) {
        return ResponseEntity.ok().body(ApiResponse.<SettingDto>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_SETTING_SUCCESS.getMessage())
                .data(settingService.updateSetting(settingRequest))
                .build());

    }

}
