package com.tma.demo.dto.response;

import com.tma.demo.common.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailResponse {
    private String roomName;
    private Integer roomRate;
    private Integer electricMeterOldNumber;
    private Integer waterMeterOldNumber;
    private RoomStatus roomStatus;
    private boolean isDelete;
    private List<UserResponse> users;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private String name;
        private String email;
    }
}
