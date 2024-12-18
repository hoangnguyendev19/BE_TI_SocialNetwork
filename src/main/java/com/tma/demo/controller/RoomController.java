package com.tma.demo.controller;

import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.*;
import com.tma.demo.dto.response.PaymentResponse;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.filter.IdFilter;
import com.tma.demo.filter.RoomFilter;
import com.tma.demo.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.EndPointConstant.*;

/**
 * RoomController
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = ROOM)
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomResponse>> createRoom(@RequestBody CreateRoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.CREATED_SUCCESS.getMessage())
                .data(roomService.createRoom(request))
                .build()
        );
    }

    @PutMapping(value = RESET_ROOM)
    public ResponseEntity<ApiResponse<RoomResponse>> resetRoom(
            @RequestParam(value = "roomId", required = true) String roomId) {
        return ResponseEntity.ok(
                ApiResponse.<RoomResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message(SuccessMessage.RESET_ROOM_SUCCESS.getMessage())
                        .data(roomService.resetRoom(roomId))
                        .build()
        );
    }

    @PutMapping(value = STATUS)
    public ResponseEntity<ApiResponse<RoomResponse>> updateRoomStatus(@RequestBody UpdateRoomStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.<RoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_SUCCESS.getMessage())
                .data(roomService.updateRoomStatus(request))
                .build());
    }
    @DeleteMapping(value = ID)
    public ResponseEntity<ApiResponse<Object>> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .code(HttpStatus.OK.value())
                        .message(SuccessMessage.DELETE_SUCCESS.getMessage())
                        .data(null)
                        .build()
        );
    }

    @PutMapping(value = PAYMENT + STATUS)
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(@RequestBody UpdatePaymentStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_SUCCESS.getMessage())
                .data(roomService.updatePaymentStatus(request))
                .build());
    }

    @PostMapping(value = PAYMENT)
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(@RequestBody CreatePaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<PaymentResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.CREATED_SUCCESS.getMessage())
                .data(roomService.createPayment(request, PaymentStatus.UNPAID))
                .build());
    }

    @PostMapping(value = VIEW_LIST)
    public ResponseEntity<ApiResponse<Page<RoomResponse>>> getListBoardingHouse(@RequestBody PagingRequest<RoomFilter> pagingRequest) {

        return ResponseEntity.ok(ApiResponse.<Page<RoomResponse>>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.GET_LIST_BOARDING_HOUSES_SUCCESS.getMessage())
                .data(roomService.getListRooms(pagingRequest))
                .build());
    }
    @PostMapping(PEOPLE)
    public ResponseEntity<ApiResponse<PeopleResponse>> addPeopleInRoom(@RequestBody PeopleRequest peopleRequest) {
        PeopleResponse peopleResponse = roomService.addPeopleToRoom(peopleRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.ADD_PEOPLE_ROOM_SUCCESS.getMessage(), peopleResponse));
    }
    @PutMapping(PEOPLE)
    public ResponseEntity<ApiResponse<PeopleResponse>> updatePeopleInRoom(@RequestBody PeopleRequest peopleRequest) {
        PeopleResponse peopleResponse = roomService.updatePeopleInRoom(peopleRequest);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.UPDATE_PEOPLE_ROOM_SUCCESS.getMessage(), peopleResponse));
    }
    @DeleteMapping(PEOPLE + ROOM_USER_ID)
    public ResponseEntity<ApiResponse<Void>> removePeopleFromRoom(@PathVariable String roomUserId ) {
        roomService.removePeopleFromRoom(roomUserId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.DELETE_SUCCESS.getMessage(), null));
    }
    @GetMapping(DETAIL+ROOM_ID)
    public ResponseEntity<ApiResponse<RoomDetailResponse>> getRoomDetail(@PathVariable String roomId) {
        RoomDetailResponse roomDetailResponse = roomService.getRoomDetail(roomId);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), SuccessMessage.GET_ROOM_DETAIL_SUCCESS.getMessage(), roomDetailResponse));
    }

}
