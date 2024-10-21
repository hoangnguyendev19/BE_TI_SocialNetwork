package com.tma.demo.controller;

import com.tma.demo.common.APIConstant;
import com.tma.demo.common.SuccessMessage;
import com.tma.demo.dto.ApiResponse;
import com.tma.demo.dto.BoardingHouseDto;
import com.tma.demo.dto.SettingBoardingHouseDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * BoardingHouseController
 * Version 1.0
 * Date: 18/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/10/2024        NGUYEN             create
 */
@RestController
@RequestMapping(value = APIConstant.BOARDING_HOUSE)
@RequiredArgsConstructor
public class BoardingHouseController {
    private final BoardingHouseService boardingHouseService;

    @PostMapping
    public ResponseEntity<ApiResponse<BoardingHouseDto>> registerBoardingHouse(@RequestBody BoardingHouseDto boardingHouseDto) {
        return ResponseEntity.ok().body(ApiResponse.<BoardingHouseDto>builder()
                .code(HttpStatus.CREATED.value())
                .message(SuccessMessage.REGISTER_SUCCESS.getMessage())
                .data(boardingHouseService.register(boardingHouseDto))
                .build());
    }

    @PostMapping(value = APIConstant.VIEW_LIST)
    public ResponseEntity<ApiResponse<Page<BoardingHouseDto>>> getListBoardingHouse(@RequestBody PagingRequest pagingRequest) {

        return ResponseEntity.ok(ApiResponse.<Page<BoardingHouseDto>>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.GET_LIST_BOARDING_HOUSES_SUCCESS.getMessage())
                .data(boardingHouseService.getListBoardingHouses(pagingRequest))
                .build());
    }

    @PutMapping(value = APIConstant.UPDATE_SETTING)
    public ResponseEntity<ApiResponse<SettingBoardingHouseDto>> saveSetting(@RequestBody SettingBoardingHouseDto settingBoardingHouseDto) {
        return ResponseEntity.ok(ApiResponse.<SettingBoardingHouseDto>builder()
                .code(HttpStatus.OK.value())
                .message(SuccessMessage.UPDATE_SETTING_SUCCESS.getMessage())
                .data(boardingHouseService.saveSetting(settingBoardingHouseDto))
                .build());
    }

}
