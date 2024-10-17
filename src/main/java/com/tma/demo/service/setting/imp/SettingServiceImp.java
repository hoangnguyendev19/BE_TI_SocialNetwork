package com.tma.demo.service.setting.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SettingKey;
import com.tma.demo.dto.SettingDto;
import com.tma.demo.entity.Setting;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.SettingRepository;
import com.tma.demo.service.setting.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * SettingServiceImp
 * Version 1.0
 * Date: 16/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 16/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class SettingServiceImp implements SettingService {
    private final SettingRepository settingRepository;

    @Override
    public SettingDto createSetting(SettingDto settingRequest) {
        Setting setting = Setting.builder()
                .key(SettingKey.valueOf(settingRequest.getKey()))
                .value(settingRequest.getValue())
                .build();
        setting = settingRepository.saveAndFlush(setting);
        return new SettingDto(setting.getKey().toString(), setting.getValue());
    }

    @Override
    public SettingDto updateSetting(SettingDto settingRequest) {
        SettingKey key = SettingKey.valueOf(settingRequest.getKey());
        Setting setting = settingRepository.findByKey(key)
                .orElseThrow(() -> new BaseException(ErrorCode.SETTING_KEY_DOES_NOT_EXIST));
        setting.setValue(settingRequest.getValue());
        setting = settingRepository.saveAndFlush(setting);
        return new SettingDto(setting.getKey().toString(), setting.getValue());
    }

    @Override
    public int getMaxReport() {
        return Integer.parseInt(settingRepository.findByKey(SettingKey.MAX_REPORTS)
                .orElseThrow(() -> new BaseException(ErrorCode.SETTING_KEY_DOES_NOT_EXIST))
                .getValue());
    }
}
