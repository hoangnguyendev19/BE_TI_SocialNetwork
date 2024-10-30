package com.tma.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryRoomResponse {
    private Integer electricMeterNumber;
    private Integer waterMeterNumber;
    private LocalDateTime createdAt;
}
