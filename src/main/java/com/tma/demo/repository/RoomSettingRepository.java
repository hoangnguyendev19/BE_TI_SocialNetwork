package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.RoomSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QRoomSetting.roomSetting;

/**
 * RoomSettingRepository
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@Component
@RequiredArgsConstructor
public class RoomSettingRepository {
    private final JPAQueryFactory query;

    public Optional<RoomSetting> findByBoardingHouseId(UUID id) {
        return Optional.ofNullable(query.selectFrom(roomSetting)
                .where(roomSetting.boardingHouse.id.eq(id))
                .fetchOne());
    }
}
