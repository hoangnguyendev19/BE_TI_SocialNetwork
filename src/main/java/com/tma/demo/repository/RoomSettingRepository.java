package com.tma.demo.repository;

import com.tma.demo.entity.RoomSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * RoomSettingRepository
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
public interface RoomSettingRepository extends JpaRepository<RoomSetting, UUID> {

    Optional<RoomSetting> findByBoardingHouseId(UUID uuid);
}
