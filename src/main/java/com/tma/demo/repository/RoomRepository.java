package com.tma.demo.repository;

import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
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
    @Query("SELECT count (r.id)  FROM Room r WHERE r.roomName = :roomName AND r.boardingHouse.id = :id AND r.isDelete != true ")
    int isRoomNameExist(@Param("roomName") String roomName, @Param("id") UUID id);

    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.isDelete != true")
    Optional<Room> findRoomById(@Param("id") UUID uuid);


    @Query(""" 
            SELECT r
            FROM Room r LEFT JOIN (
                     SELECT p.room.id as rid, p.paymentStatus as status
                     FROM Payment p
                     WHERE p.createdAt = (
                         SELECT MAX(p2.createdAt)
                         FROM Payment p2
                         WHERE p2.room.id = p.room.id
                     )
                 ) latest_payment ON latest_payment.rid = r.id
                 WHERE r.boardingHouse.id = :boardingHouseId and r.isDelete != true
                 AND (r.roomStatus = :roomStatus or :roomStatus is null)
                 AND (latest_payment.status = :paymentStatus or :paymentStatus is null)
                 AND (Date(r.createdAt) = Date(:createdAt) or :createdAt = '')
            """)
    Page<Room> getAllRooms(Pageable pageable,
                           @Param("boardingHouseId") UUID boardingHouseId,
                           @Param("paymentStatus") PaymentStatus paymentStatus,
                           @Param("roomStatus") RoomStatus roomStatus,
                           @Param("createdAt") String createdAt
    );
}
