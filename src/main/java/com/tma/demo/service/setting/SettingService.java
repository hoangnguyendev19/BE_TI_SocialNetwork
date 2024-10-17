package com.tma.demo.service.setting;

import com.tma.demo.dto.SettingDto;
import org.springframework.stereotype.Service;

@Service
public interface SettingService {
    SettingDto createSetting(SettingDto settingRequest);

    SettingDto updateSetting(SettingDto settingRequest);

    int getMaxReport();
}
