package com.tma.demo.service.boarding_house;

import com.tma.demo.dto.BoardingHouseDto;
import com.tma.demo.dto.SettingBoardingHouseDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.entity.BoardingHouse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface BoardingHouseService {
    BoardingHouseDto register(BoardingHouseDto boardingHouse);

    Page<BoardingHouseDto> getListBoardingHouses(PagingRequest pagingRequest);

    SettingBoardingHouseDto saveSetting(SettingBoardingHouseDto settingBoardingHouseDto);

    BoardingHouse getBoardingHouse(String boardingHouseId);
}
