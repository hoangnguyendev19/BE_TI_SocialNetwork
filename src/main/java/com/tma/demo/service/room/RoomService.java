package com.tma.demo.service.room;

import com.tma.demo.dto.request.CreatePaymentRequest;
import com.tma.demo.dto.request.CreateRoomRequest;
import com.tma.demo.dto.request.UpdatePaymentStatusRequest;
import com.tma.demo.dto.request.UpdateRoomStatusRequest;
import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.entity.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * RoomService
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@Service
public interface RoomService {
    RoomResponse createRoom(CreateRoomRequest request);

    RoomResponse resetRoom(String roomId);

    Room getRoomById(String roomId);

    RoomResponse updateRoomStatus(UpdateRoomStatusRequest request);

    void deleteRoom(String id);

    @Transactional
    PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest);

    @Transactional
    PaymentResponse updatePaymentStatus(UpdatePaymentStatusRequest updatePaymentStatusRequest);
}
