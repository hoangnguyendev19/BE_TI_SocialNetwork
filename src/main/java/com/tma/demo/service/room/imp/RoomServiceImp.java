package com.tma.demo.service.room.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.dto.request.CreatePaymentRequest;
import com.tma.demo.dto.request.CreateRoomRequest;
import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.entity.BoardingHouse;
import com.tma.demo.entity.Room;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.HistoryRoomRepository;
import com.tma.demo.repository.RoomRepository;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.payment.PaymentService;
import com.tma.demo.service.room.RoomService;
import com.tma.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * RoomServiceImp
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
public class RoomServiceImp implements RoomService {
    private final BoardingHouseService boardingHouseService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserService userService;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public RoomResponse createRoom(CreateRoomRequest request) {
        checkRoomName(request);
        BoardingHouse boardingHouse = boardingHouseService.getBoardingHouse(request.getBoardingHouseId());
        checkAth(boardingHouse.getUser().getId().toString());
        Room room = Room.builder()
                .boardingHouse(boardingHouse)
                .roomName(request.getRoomName())
                .electricMeterOldNumber(request.getElectricityMeterOldNumber())
                .roomRate(0)
                .roomStatus(RoomStatus.valueOf(request.getRoomStatus().toUpperCase()))
                .waterMeterOldNumber(request.getWaterMeterOldNumber())
                .isDelete(false)
                .build();
        room = roomRepository.saveAndFlush(room);
        CreatePaymentRequest createPaymentRequest =
                new CreatePaymentRequest(room.getId().toString(), room.getRoomRate(), room.getElectricMeterOldNumber(), room.getWaterMeterOldNumber());
        PaymentResponse paymentResponse = paymentService.createPayment(createPaymentRequest);
        return roomMapper.from(room);
    }

    @Override
    @Transactional
    public RoomResponse resetRoom(String roomId) {
        Room room = getRoomById(roomId);
        checkAth(room.getBoardingHouse().getUser().getId().toString());
        room.setRoomRate(null);
        room.setRoomStatus(RoomStatus.ROOM_AVAILABLE);
        room.setElectricMeterOldNumber(0);
        room.setWaterMeterOldNumber(0);
        return roomMapper.from(roomRepository.saveAndFlush(room));
    }

    public Room getRoomById(String roomId) {
        return roomRepository.findRoomById(UUID.fromString(roomId)).orElseThrow(()
                -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
    }

    private void checkRoomName(CreateRoomRequest request) {
        if (isRoomNameExist(request.getRoomName())) {
            throw new BaseException(ErrorCode.ROOM_NAME_ALREADY_EXIST);
        }
    }

    private boolean isRoomNameExist(String roomName) {
        return roomRepository.isRoomNameExist(roomName);
    }

    private void checkAth(String userId) {
        if (!userId.equals(userService.getUserDetails().getId().toString())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
    }
}
