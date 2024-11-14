package com.tma.demo.service.room.imp;

import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.entity.Payment;
import com.tma.demo.entity.Room;
import com.tma.demo.repository.RoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Mapper
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class Mapper {
    private final RoomUserRepository roomUserRepository;

    RoomResponse from(Room room, PaymentResponse paymentResponse) {
        int totalPeople = roomUserRepository.getTotalPeople(room.getId());
        return RoomResponse.builder()
                .id(room.getId().toString())
                .boardingHouseId(room.getBoardingHouse().getId().toString())
                .roomName(room.getRoomName())
                .roomRate(room.getRoomRate())
                .numberOfPeople(totalPeople)
                .roomStatus(room.getRoomStatus().toString())
                .payment(paymentResponse)
                .waterMeterOldNumber(room.getWaterMeterOldNumber())
                .electricityMeterOldNumber(room.getElectricMeterOldNumber())
                .createdAt(room.getCreatedAt())
                .build();
    }

    PaymentResponse from(Payment payment) {
        return new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getElectricityMeterNewNumber(), payment.getWaterMeterNewNumber(), payment.getTotalAmount());
    }
}
