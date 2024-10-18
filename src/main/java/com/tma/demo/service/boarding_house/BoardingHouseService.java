package com.tma.demo.service.boarding_house;

import com.tma.demo.dto.BoardingHouseDto;
import org.springframework.stereotype.Service;

@Service
public interface BoardingHouseService {
    BoardingHouseDto register(BoardingHouseDto boardingHouse);
}
