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
    @Query("SELECT count (r.id)  FROM Room r WHERE r.roomName = :roomName AND r.boardingHouse.id = :id AND r.isDelete != true ")
    int isRoomNameExist(@Param("roomName") String roomName, @Param("id") UUID id);

    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.isDelete != true")
    Optional<Room> findRoomById(@Param("id") UUID uuid);


    @Query(value = """
            SELECT *
            FROM Room r
            WHERE r.boarding_house_id = :boardingHouseId AND r.is_delete != true
            AND (r.room_status = :roomStatus or :roomStatus is NULL )
            AND ((select p.payment_status
                        from Payment p
                        where p.room_id = r.id
                        order by p.created_at desc LIMIT 1 ) = :paymentStatus or :paymentStatus is null)
            AND (:date is null or DATE(r.created_at) = DATE(:date))
            """, nativeQuery = true)
    Page<Room> getAllRooms(Pageable pageable,
                           @Param("boardingHouseId") UUID boardingHouseId,
                           @Param("paymentStatus") String paymentStatus,
                           @Param("roomStatus") String roomStatus,
                           @Param("date") String date
    );
}
