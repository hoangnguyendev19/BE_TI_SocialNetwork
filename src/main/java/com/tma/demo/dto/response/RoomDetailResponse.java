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
    private Integer electricMeterNewNumber;
    private Integer waterMeterNewNumber;
    private boolean isDelete;
    private List<UserReponseRoom> users;
}

