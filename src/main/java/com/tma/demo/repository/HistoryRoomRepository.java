package com.tma.demo.repository;

import com.tma.demo.entity.HistoryRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
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
    @Query(value = "SELECT hr FROM HistoryRoom hr WHERE hr.room.id = :roomId ORDER BY hr.createdAt DESC")
    List<HistoryRoom> findTop2ByRoom_Id(@Param("roomId") UUID roomId, Pageable pageable);
}
