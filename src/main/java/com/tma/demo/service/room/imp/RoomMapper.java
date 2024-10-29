package com.tma.demo.service.room.imp;

import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * RoomMapper
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
public class RoomMapper {
    private final RoomUserRepository userRepository;
    RoomResponse from(Room room, PaymentResponse paymentResponse){
        return RoomResponse.builder()
                .id(room.getId().toString())
                .boardingHouseId(room.getBoardingHouse().getId().toString())
                .roomName(room.getRoomName())
                .roomRate(room.getRoomRate())
                .roomStatus(room.getRoomStatus().toString())
                .payment(paymentResponse)
                .waterMeterOldNumber(room.getWaterMeterOldNumber())
                .electricityMeterOldNumber(room.getElectricMeterOldNumber())
                .createdAt(room.getCreatedAt())
                .build();
    }
}
