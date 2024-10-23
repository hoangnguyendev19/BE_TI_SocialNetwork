package com.tma.demo.repository;

import com.tma.demo.entity.RoomSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("select r from RoomSetting r WHERE  r.boardingHouse.id = :id")
    Optional<RoomSetting> findByBoardingHouseId(@Param("id") UUID id);
}
