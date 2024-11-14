package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRoomResponse {
    private Integer electricMeterOldNumber;
    private Integer waterMeterOldNumber;
    private LocalDateTime createdAt;
}
