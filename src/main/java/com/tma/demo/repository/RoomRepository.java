package com.tma.demo.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.entity.Room;
import com.tma.demo.util.BuilderRoomPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.entity.QRoom.room;

/**
 * RoomRepository
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
public class RoomRepository {
    private final JPAQueryFactory query;

    public long isRoomNameExist(String roomName, UUID id) {
        return  query.select(room.id.count())
                .from(room)
                .where(room.roomName.eq(roomName).and(room.boardingHouse.id.eq(id)))
                .fetchOne();
    }

    public Optional<Room> findRoomById(UUID id) {
        return Optional.ofNullable(query.selectFrom(room)
                .where(room.id.eq(id).and(room.isDelete.isFalse()))
                .fetchOne());
    }

    public Page<Room> getAllRooms(Pageable pageable,
                                  UUID boardingHouseId,
                                  PaymentStatus paymentStatus,
                                  RoomStatus roomStatus,
                                  String createdAt
    ) {
        Predicate predicate = BuilderRoomPredicate.buildRoomPredicate(boardingHouseId, paymentStatus, roomStatus, createdAt);
        OrderSpecifier<?> orderSpecifier = getSortOrder(pageable);
        List<Room> results = query
                .selectFrom(room)
                .where(predicate)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = query
                .select(room.id.count())
                .from(room)
                .where(predicate)
                .fetchOne();
        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?> getSortOrder(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return null;
        }
        return pageable.getSort().stream()
                .map(order -> {
                    return switch (order.getProperty()) {
                        case "createdAt" -> order.isAscending() ? room.createdAt.asc() : room.createdAt.desc();
                        case "roomName" -> order.isAscending() ? room.roomName.asc() : room.roomName.desc();
                        case "electricMeterOldNumber" -> order.isAscending() ?
                                room.electricMeterOldNumber.asc() : room.electricMeterOldNumber.desc();
                        case "waterMeterOldNumber" -> order.isAscending() ?
                                room.waterMeterOldNumber.asc() : room.waterMeterOldNumber.desc();
                        case "roomStatus" -> order.isAscending() ? room.roomStatus.asc() : room.roomStatus.desc();
                        case "isDelete" -> order.isAscending() ? room.isDelete.asc() : room.isDelete.desc();
                        default -> null;
                    };
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
