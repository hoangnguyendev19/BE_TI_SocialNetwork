package com.tma.demo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.constant.FieldConstant;
import com.tma.demo.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.tma.demo.constant.FieldConstant.*;
import static com.tma.demo.entity.QPayment.payment;
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
        Long total = query.select(room.id.count())
                .from(room)
                .where(room.roomName.eq(roomName).and(room.boardingHouse.id.eq(id)))
                .fetchOne();
        if(ObjectUtils.isEmpty(total)){
            return 0;
        }
        return total;
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
                                  LocalDate createdAt
    ) {

        OrderSpecifier<?> orderSpecifier = getSortOrder(pageable);
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(room.boardingHouse.id.eq(boardingHouseId));

        if (!ObjectUtils.isEmpty(roomStatus)) {
            predicate.and(room.roomStatus.eq(roomStatus));
        }

        if (!ObjectUtils.isEmpty(paymentStatus)) {
            predicate.and(JPAExpressions.select(payment.paymentStatus)
                    .from(payment)
                    .where(payment.room.id.eq(room.id))
                    .orderBy(payment.createdAt.desc())
                    .limit(1)
                    .eq(paymentStatus));
        }

        if (!ObjectUtils.isEmpty(createdAt)) {
            predicate.and(room.createdAt.year().eq(createdAt.getYear())
                    .and(room.createdAt.month().eq(createdAt.getMonthValue()))
                    .and(room.createdAt.dayOfMonth().eq(createdAt.getDayOfMonth())));
        }
        List<Room> results = query
                .selectFrom(room)
                .where(predicate)
                .orderBy(orderSpecifier)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = query
                .select(room.id.count())
                .from(room)
                .where(predicate)
                .fetchOne();
        if(ObjectUtils.isEmpty(total)){
            total = 0L;
        }
        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?> getSortOrder(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return null;
        }
        return pageable.getSort().stream()
                .map(order -> {
                    return switch (order.getProperty()) {
                        case CREATED_AT -> order.isAscending() ? room.createdAt.asc() : room.createdAt.desc();
                        case ROOM_NAME -> order.isAscending() ? room.roomName.asc() : room.roomName.desc();
                        case ELECTRIC_METER_OLD_NUMBER -> order.isAscending() ?
                                room.electricMeterOldNumber.asc() : room.electricMeterOldNumber.desc();
                        case WATER_METER_OLD_NUMBER -> order.isAscending() ?
                                room.waterMeterOldNumber.asc() : room.waterMeterOldNumber.desc();
                        case ROOM_STATUS -> order.isAscending() ? room.roomStatus.asc() : room.roomStatus.desc();
                        case IS_DELETE -> order.isAscending() ? room.isDelete.asc() : room.isDelete.desc();
                        default -> null;
                    };
                })
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}

