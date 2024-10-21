package com.tma.demo.controller;

import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.request.CreateRoomRequest;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.tma.demo.common.APIConstant.RESET_ROOM;
import static com.tma.demo.common.APIConstant.ROOM;

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
}
