package com.tma.demo.service.room;

import com.tma.demo.dto.request.CreateRoomRequest;
import com.tma.demo.dto.response.RoomResponse;
import com.tma.demo.entity.Room;
import org.springframework.stereotype.Service;

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
}
