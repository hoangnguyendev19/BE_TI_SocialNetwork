package com.tma.demo.repository;

import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * RoomUserRepository
 * Version 1.0
 * Date: 29/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 29/10/2024        NGUYEN             create
 */
public interface RoomUserRepository extends JpaRepository<RoomUser, UUID> {
    boolean existsByFullName(String fullName);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<RoomUser> findByRoomAndFullName(Room room, String fullName);

    Optional<RoomUser> findByRoomAndPhoneNumber(Room room, String phoneNumber);

    List<RoomUser> findByRoom(Room room);

    @Query("SELECT count (r.id) FROM RoomUser r WHERE r.room.id = :id and r.isDelete != true ")
    int getTotalPeople(@Param("id") UUID id);
}
