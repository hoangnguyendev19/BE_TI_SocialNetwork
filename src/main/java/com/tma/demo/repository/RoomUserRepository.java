package com.tma.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
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

    public List<RoomUser> findByRoomAndIsDeleteFalse(Room room) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.eq(room), isDeletePredicate())
                .fetch();
    }

    public long getTotalPeople(UUID id) {
        Long total = query.select(roomUser.id.count())
                .from(roomUser)
                .where(roomUser.room.id.eq(id), isDeletePredicate())
                .fetchOne();
        if (ObjectUtils.isEmpty(total)) {
            return 0;
        }
        return total;
    }

    public List<RoomUser> findByRoomId(UUID id) {
        return query.selectFrom(roomUser)
                .where(roomUser.room.id.eq(id), isDeletePredicate())
                .fetch();
    }

    private Predicate isDeletePredicate() {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(roomUser.isDelete.isFalse());
        return predicate;
    }

}
