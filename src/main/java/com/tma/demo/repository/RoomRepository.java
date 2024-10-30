package com.tma.demo.repository;

import com.tma.demo.entity.QRoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
@Repository
@RequiredArgsConstructor
public class RoomRepository {
//    private final JPAQueryFac queryFactory;

    //    @Query("SELECT count (r.id)  FROM Room r WHERE r.roomName = :roomName AND r.boardingHouse.id = :id AND r.isDelete != true ")
    int isRoomNameExist(@Param("roomName") String roomName, @Param("id") UUID id) {
        QRoomUser roomUser = QRoomUser.roomUser;


        return 1;
    }

//    @Query("SELECT r FROM Room r WHERE r.id = :id AND r.isDelete != true")
//    Optional<Room> findRoomById(@Param("id") UUID uuid);
//
//
//    @Query(value = """
//            SELECT *
//            FROM Room r
//            WHERE r.boarding_house_id = :boardingHouseId AND r.is_delete != true
//            AND (r.room_status = :roomStatus or :roomStatus is NULL )
//            AND ((select p.payment_status
//                        from Payment p
//                        where p.room_id = r.id
//                        order by p.created_at desc LIMIT 1 ) = :paymentStatus or :paymentStatus is null)
//            AND (:date is null or DATE(r.created_at) = DATE(:date))
//            """, nativeQuery = true)
//    Page<Room> getAllRooms(Pageable pageable,
//                           @Param("boardingHouseId") UUID boardingHouseId,
//                           @Param("paymentStatus") String paymentStatus,
//                           @Param("roomStatus") String roomStatus,
//                           @Param("date") String date
//    );
}
