package com.tma.demo.util;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.UUID;

import static com.tma.demo.entity.QPayment.payment;
import static com.tma.demo.entity.QRoom.room;

/**
 * BuilderRoomPredicate
 * Version 1.0
 * Date: 14/11/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 14/11/2024        NGUYEN             create
 */
@RequiredArgsConstructor
public class BuilderRoomPredicate {

    public static Predicate buildRoomPredicate(
            UUID boardingHouseId,
            PaymentStatus paymentStatus,
            RoomStatus roomStatus,
            String createdAt) {

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
            LocalDate localDate = LocalDate.parse(createdAt);
            predicate.and(room.createdAt.year().eq(localDate.getYear())
                    .and(room.createdAt.month().eq(localDate.getMonthValue()))
                    .and(room.createdAt.dayOfMonth().eq(localDate.getDayOfMonth())));
        }

        return predicate;
    }


}
