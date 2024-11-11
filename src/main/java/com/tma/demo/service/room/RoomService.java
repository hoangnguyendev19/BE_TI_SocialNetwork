package com.tma.demo.service.room;

import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.*;
import com.tma.demo.entity.Room;
import com.tma.demo.filter.IdFilter;
import org.springframework.data.domain.Page;
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

    Page<RoomResponse> getListRooms(PagingRequest<IdFilter> pagingRequest);

    PeopleResponse addPeopleToRoom(PeopleRequest request);

    UpdatePeopleResponse updatePeopleInRoom(UpdatePeopleRequest PeopleRequest);

    void removePeopleFromRoom(String roomUserId);

    RoomDetailResponse getRoomDetail(String roomId);


}
