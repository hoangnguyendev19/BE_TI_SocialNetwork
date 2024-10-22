package com.tma.demo.service.payment.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.dto.request.CreatePaymentRequest;
import com.tma.demo.dto.request.UpdatePaymentStatusRequest;
import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.entity.HistoryRoom;
import com.tma.demo.entity.Payment;
import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomSetting;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.HistoryRoomRepository;
import com.tma.demo.repository.PaymentRepository;
import com.tma.demo.repository.RoomRepository;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.payment.PaymentService;
import com.tma.demo.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * PaymentServiceImp
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RoomService roomService;
    private final BoardingHouseService boardingHouseService;
    private final RoomRepository roomRepository;
    private final HistoryRoomRepository historyRoomRepository;

    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
        Room room = roomService.getRoomById(createPaymentRequest.getRoomId());
        RoomSetting roomSetting = boardingHouseService.getSetting(room.getBoardingHouse().getId().toString());
        int totalAmount = calTotalAmount(createPaymentRequest, roomSetting, room);
        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.UNPAID)
                .room(room)
                .totalAmount(totalAmount)
                .build();
        payment = paymentRepository.saveAndFlush(payment);
        updateRoom(createPaymentRequest, room);
        createHistoryRoom(room);
        return new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getTotalAmount());
    }

    private void updateRoom(CreatePaymentRequest createPaymentRequest, Room room) {
        room.setRoomRate(createPaymentRequest.getRoomRate());
        room.setWaterMeterOldNumber(createPaymentRequest.getWaterMeterNewNumber());
        room.setElectricMeterOldNumber(createPaymentRequest.getElectricityMeterNewNumber());
        roomRepository.save(room);
    }

    private void createHistoryRoom(Room room) {
        HistoryRoom historyRoom = HistoryRoom.builder()
                .room(room)
                .electricMeterNumber(room.getElectricMeterOldNumber())
                .waterMeterNumber(room.getWaterMeterOldNumber())
                .isDelete(false)
                .build();
        historyRoomRepository.save(historyRoom);
    }

    @Override
    @Transactional
    public PaymentResponse updatePaymentStatus(UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        Payment payment = paymentRepository.findById(UUID.fromString(updatePaymentStatusRequest.getId()))
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
        payment.setPaymentStatus(PaymentStatus.valueOf(updatePaymentStatusRequest.getStatus().toUpperCase()));

        payment = paymentRepository.saveAndFlush(payment);
        return new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getTotalAmount());
    }

    private int calTotalAmount(CreatePaymentRequest createPaymentRequest, RoomSetting roomSetting, Room room) {
        if (ObjectUtils.isEmpty(roomSetting)) {
            return 0;
        }
        return createPaymentRequest.getRoomRate()
                + (createPaymentRequest.getElectricityMeterNewNumber() - room.getElectricMeterOldNumber()) * roomSetting.getElectricBill()
                + (createPaymentRequest.getWaterMeterNewNumber() - room.getWaterMeterOldNumber()) * roomSetting.getWaterBill();
    }

}
