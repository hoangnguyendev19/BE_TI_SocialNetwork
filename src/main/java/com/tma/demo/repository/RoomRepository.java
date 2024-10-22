package com.tma.demo.repository;

import com.tma.demo.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * RoomRepository
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
public interface RoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT count (r.id)  FROM Room r WHERE r.roomName = :roomName AND r.isDelete != true ")
    int isRoomNameExist(String roomName);
    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.isDelete != true")
    Optional<Room> findRoomById(@Param("id") UUID uuid);
    @Query("SELECT r FROM Room r WHERE r.isDelete != true")
    Page<Room> getAllRooms(Pageable pageable);
}
