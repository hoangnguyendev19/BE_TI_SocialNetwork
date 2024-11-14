package com.tma.demo.service.boarding_house.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.SettingKey;
import com.tma.demo.dto.BoardingHouseDto;
import com.tma.demo.dto.SettingBoardingHouseDto;
import com.tma.demo.entity.BoardingHouse;
import com.tma.demo.entity.RoomSetting;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.BoardingHouseRepository;
import com.tma.demo.repository.ISettingRepository;
import com.tma.demo.repository.IRoomSettingRepository;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.setting.SettingService;
import com.tma.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
    private final IRoomSettingRepository roomSettingRepository;
    private final SettingService settingService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ISettingRepository iSettingRepository;

    @Override
    @Transactional
    public BoardingHouseDto register(BoardingHouseDto request) {
        User user = userService.getUserDetails();
        if (isUserRegistered(user)) {
            throw new BaseException(ErrorCode.USER_REGISTERED);
        }
        if (isBoardingHouseNameExists(request.getBoardingHouseName())) {
            throw new BaseException(ErrorCode.BOARDING_HOUSE_NAME_ALREADY_EXISTS);
        }
        BoardingHouse boardingHouse = BoardingHouse.builder()
                .boardingHouseName(request.getBoardingHouseName())
                .user(user)
                .city(request.getCity())
                .ward(request.getWard())
                .isDelete(false)
                .presentAddress(request.getPresentAddress())
                .build();
        approveBoardingHouseAsync(boardingHouse);
        return modelMapper.map(boardingHouse, BoardingHouseDto.class);
    }

    private boolean isUserRegistered(User user) {
        return boardingHouseRepository.findByUser(user.getId()).isPresent();
    }

    @Transactional
    void approveBoardingHouseAsync(BoardingHouse boardingHouse) {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(Integer.parseInt(settingService.getValue(SettingKey.APPROVE_TIME)));  // Simulate delay
                BoardingHouse temp = boardingHouseRepository.saveAndFlush(boardingHouse);
                SettingBoardingHouseDto settingBoardingHouseDto = new SettingBoardingHouseDto(
                        temp.getId().toString(), 0, 0
                );
                saveSetting(settingBoardingHouseDto);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Override
    @Transactional
    public SettingBoardingHouseDto saveSetting(SettingBoardingHouseDto settingBoardingHouseDto) {
        BoardingHouse boardingHouse = getBoardingHouse(settingBoardingHouseDto.getBoardingHouseId());
        RoomSetting roomSetting = roomSettingRepository.findByBoardingHouseId(UUID.fromString(settingBoardingHouseDto.getBoardingHouseId()))
                .orElse(null);
        if (ObjectUtils.isEmpty(roomSetting)) {
            roomSetting = RoomSetting.builder()
                    .boardingHouse(boardingHouse)
                    .electricBill(settingBoardingHouseDto.getElectricityBill())
                    .waterBill(settingBoardingHouseDto.getWaterBill())
                    .build();
        } else {
            roomSetting.setWaterBill(settingBoardingHouseDto.getWaterBill());
            roomSetting.setElectricBill(settingBoardingHouseDto.getElectricityBill());
        }
        roomSettingRepository.save(roomSetting);
        return settingBoardingHouseDto;
    }

    private Boolean isBoardingHouseNameExists(String boardingHouseName) {
        return boardingHouseRepository.isBoardingHouseNameExists(boardingHouseName) > 0;
    }

    @Override
    public BoardingHouse getBoardingHouse(String id) {
        return boardingHouseRepository.findBoardingHouseById(UUID.fromString(id));
    }

    @Override
    public RoomSetting getSetting(String boardingHouseId) {
        return roomSettingRepository.findByBoardingHouseId(UUID.fromString(boardingHouseId))
                .orElse(null);
    }

    public SettingBoardingHouseDto getSetting() {
        User user = userService.getUserDetails();
        BoardingHouse boardingHouse = boardingHouseRepository.findByUser(user.getId()).orElseThrow(() -> new BaseException(ErrorCode.ROOM_SETTING_NOT_FOUND));
        RoomSetting setting = getSetting(boardingHouse.getId().toString());
        return new SettingBoardingHouseDto(setting.getBoardingHouse().getId().toString(), setting.getWaterBill(), setting.getElectricBill());
    }

    @Override
    public BoardingHouseDto getBoardingHouses() {
        User user = userService.getUserDetails();
        Optional<BoardingHouse> boardingHouse = boardingHouseRepository.findByUser(user.getId());
        SettingBoardingHouseDto setting = getSetting();
        return boardingHouse.map(house -> BoardingHouseDto.builder()
                .id(house.getId().toString())
                .ward(house.getWard())
                .boardingHouseName(house.getBoardingHouseName())
                .city(house.getCity())
                .presentAddress(house.getPresentAddress())
                .setting(setting)
                .build()).orElse(null);
    }
}
