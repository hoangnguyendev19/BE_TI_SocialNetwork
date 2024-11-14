package com.tma.demo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QRoomUser.roomUser;

/**
 * RoomUserRepository
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@Repository
@RequiredArgsConstructor
public class RoomUserRepository {
    private final JPAQueryFactory query;

    boolean existsByFullName(String fullName) {
        return query.select(roomUser.id.count())
                .from(roomUser)
                .where(roomUser.fullName.eq(fullName))
                .fetchOne() > 0;
    }

    boolean existsByPhoneNumber(String phoneNumber) {
        return query.selectFrom(roomUser).where(roomUser.phoneNumber.eq(phoneNumber))
                .stream().findAny().isPresent();
    }

    Optional<RoomUser> findByRoomAndFullName(Room room, String fullName) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.eq(room).and(roomUser.fullName.eq(fullName)))
                .stream().findFirst();
    }

    Optional<RoomUser> findByRoomAndPhoneNumber(Room room, String phoneNumber) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.eq(room).and(roomUser.phoneNumber.eq(phoneNumber)))
                .stream().findFirst();
    }

    List<RoomUser> findByRoom(Room room) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.eq(room))
                .stream().toList();
    }

    public List<RoomUser> findByRoomAndIsDeleteFalse(Room room) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.eq(room).and(roomUser.isDelete.isFalse()))
                .stream().toList();
    }

    public long getTotalPeople(UUID id) {
        return query.select(roomUser.id.count())
                .from(roomUser)
                .where(roomUser.room.id.eq(id).and(roomUser.isDelete.isFalse()))
                .fetchOne();
    }


    public List<RoomUser> findByRoomId(UUID id) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.id.eq(id).and(roomUser.isDelete).isFalse())
                .fetch();
    }
}
