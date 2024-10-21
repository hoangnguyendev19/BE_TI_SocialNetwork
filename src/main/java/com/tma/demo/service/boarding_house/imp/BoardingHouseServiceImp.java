package com.tma.demo.service.boarding_house.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.dto.BoardingHouseDto;
import com.tma.demo.dto.SettingBoardingHouseDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.entity.BoardingHouse;
import com.tma.demo.entity.RoomSetting;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.BoardingHouseRepository;
import com.tma.demo.repository.RoomSettingRepository;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * BoardingHouseServiceImp
 * Version 1.0
 * Date: 18/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class BoardingHouseServiceImp implements BoardingHouseService {
    private final BoardingHouseRepository boardingHouseRepository;
    private final RoomSettingRepository roomSettingRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BoardingHouseDto register(BoardingHouseDto request) {
        if (isBoardingHouseNameExists(request.getBoardingHouseName())) {
            throw new BaseException(ErrorCode.BOARDING_HOUSE_NAME_ALREADY_EXISTS);
        }
        BoardingHouse boardingHouse = BoardingHouse.builder()
                .boardingHouseName(request.getBoardingHouseName())
                .user(userService.getUserDetails())
                .city(request.getCity())
                .ward(request.getWard())
                .isDelete(false)
                .presentAddress(request.getPresentAddress())
                .build();
        boardingHouse = boardingHouseRepository.saveAndFlush(boardingHouse);
        return modelMapper.map(boardingHouse, BoardingHouseDto.class);
    }

    @Override
    public Page<BoardingHouseDto> getListBoardingHouses(PagingRequest pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<BoardingHouse> page = boardingHouseRepository.findAll(pageable);
        List<BoardingHouseDto> result = page.stream()
                .map(boardingHouse -> modelMapper.map(boardingHouse, BoardingHouseDto.class))
                .toList();
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public SettingBoardingHouseDto saveSetting(SettingBoardingHouseDto settingBoardingHouseDto) {
        BoardingHouse boardingHouse = getBoardingHouse(settingBoardingHouseDto.getBoardingHouseId());
        var roomSetting = roomSettingRepository.findByBoardingHouseId(UUID.fromString(settingBoardingHouseDto.getBoardingHouseId()));
        if (roomSetting.isEmpty()) {
            RoomSetting temp = RoomSetting.builder()
                    .boardingHouse(boardingHouse)
                    .electricBill(settingBoardingHouseDto.getElectricityBill())
                    .waterBill(settingBoardingHouseDto.getWaterBill())
                    .build();
            roomSettingRepository.save(temp);
        }
        else {
            roomSetting.get().setWaterBill(settingBoardingHouseDto.getWaterBill());
            roomSetting.get().setElectricBill(settingBoardingHouseDto.getElectricityBill());
            roomSettingRepository.save(roomSetting.get());
        }
        return settingBoardingHouseDto;
    }

    private Boolean isBoardingHouseNameExists(String boardingHouseName) {
        return boardingHouseRepository.isBoardingHouseNameExists(boardingHouseName);
    }

    public BoardingHouse getBoardingHouse(String id) {
        return boardingHouseRepository.findBoardingHouseById(UUID.fromString(id));
    }
}
