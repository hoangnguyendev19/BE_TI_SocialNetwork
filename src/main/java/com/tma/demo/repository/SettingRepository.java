package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.common.SettingKey;
import com.tma.demo.entity.Setting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.tma.demo.entity.QSetting.setting;

/**
 * SettingRepository
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@Repository
@RequiredArgsConstructor
public class SettingRepository {
    private final JPAQueryFactory query;

    public Optional<Setting> findByKey(SettingKey key) {
        return query.selectFrom(setting).where(setting.key.eq(key)).stream().findFirst();
    }
}
