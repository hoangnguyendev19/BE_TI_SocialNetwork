package com.tma.demo.repository;

import com.tma.demo.entity.HistoryRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * HistoryRoomRepository
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
public interface HistoryRoomRepository extends JpaRepository<HistoryRoom, UUID> {
}
