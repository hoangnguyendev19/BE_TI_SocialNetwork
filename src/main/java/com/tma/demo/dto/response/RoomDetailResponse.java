package com.tma.demo.dto.response;

import com.tma.demo.common.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse {
    private String roomName;
    private Integer roomRate;
    private Integer electricMeterOldNumber;
    private Integer waterMeterOldNumber;
    private List<HistoryRoomResponse> historyRooms;
    private RoomStatus roomStatus;
    private boolean isDelete;
    private List<UserReponseRoom> users;
}

