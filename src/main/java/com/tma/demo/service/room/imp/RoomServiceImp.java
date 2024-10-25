package com.tma.demo.service.room.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.*;
import com.tma.demo.entity.*;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.*;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.room.RoomService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * RoomServiceImp
 * Version 1.0
 * Date: 21/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 21/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImp implements RoomService {
    private final BoardingHouseService boardingHouseService;
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserService userService;
    private final PaymentRepository paymentRepository;
    private final HistoryRoomRepository historyRoomRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;

    @Override
    @Transactional
    public RoomResponse createRoom(CreateRoomRequest request) {
        checkRoomName(request);
        BoardingHouse boardingHouse = boardingHouseService.getBoardingHouse(request.getBoardingHouseId());
        checkAth(boardingHouse.getUser().getId().toString());
        Room room = Room.builder()
                .boardingHouse(boardingHouse)
                .roomName(request.getRoomName())
                .electricMeterOldNumber(request.getElectricityMeterOldNumber())
                .roomRate(0)
                .roomStatus(RoomStatus.valueOf(request.getRoomStatus().toUpperCase()))
                .waterMeterOldNumber(request.getWaterMeterOldNumber())
                .isDelete(false)
                .build();
        room = roomRepository.saveAndFlush(room);
        PaymentResponse paymentResponse = createPayment(new CreatePaymentRequest(room.getId().toString(), room.getRoomRate(), room.getElectricMeterOldNumber(), room.getWaterMeterOldNumber()));
        return roomMapper.from(room, paymentResponse);
    }

    @Override
    @Transactional
    public RoomResponse resetRoom(String roomId) {
        Room room = getRoomById(roomId);
        checkAth(room.getBoardingHouse().getUser().getId().toString());
        room.setRoomRate(null);
        room.setRoomStatus(RoomStatus.ROOM_AVAILABLE);
        room.setElectricMeterOldNumber(0);
        room.setWaterMeterOldNumber(0);
        Payment payment = Payment.builder()
                .totalAmount(0)
                .room(room)
                .paymentStatus(PaymentStatus.UNPAID)
                .build();
        payment = paymentRepository.saveAndFlush(payment);
        PaymentResponse paymentResponse = new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getTotalAmount());
        return roomMapper.from(roomRepository.saveAndFlush(room), paymentResponse);
    }

    @Override
    public RoomResponse updateRoomStatus(UpdateRoomStatusRequest request) {
        Room room = getRoomById(request.getId());
        room.setRoomStatus(RoomStatus.valueOf(request.getStatus().toUpperCase()));
        room = roomRepository.saveAndFlush(room);
        return roomMapper.from(room, getPaymentResponse(request.getId()));
    }

    @Override
    public void deleteRoom(String id) {
        Room room = getRoomById(id);
        room.setDelete(true);
        roomRepository.save(room);
    }

    @Override
    public Room getRoomById(String roomId) {
        return roomRepository.findRoomById(UUID.fromString(roomId)).orElseThrow(()
                -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
    }

    private void checkRoomName(CreateRoomRequest request) {
        if (isRoomNameExist(request.getRoomName())) {
            throw new BaseException(ErrorCode.ROOM_NAME_ALREADY_EXIST);
        }
    }

    private boolean isRoomNameExist(String roomName) {
        return roomRepository.isRoomNameExist(roomName) > 0;
    }

    private void checkAth(String userId) {
        if (!userId.equals(userService.getUserDetails().getId().toString())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
    }

    //PAYMENT
    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest) {
        Room room = getRoomById(createPaymentRequest.getRoomId());
        RoomSetting roomSetting = boardingHouseService.getSetting(room.getBoardingHouse().getId().toString());
        int totalAmount = calTotalAmount(createPaymentRequest, roomSetting, room);
        Payment payment = Payment.builder()
                .paymentStatus(PaymentStatus.UNPAID)
                .room(room)
                .totalAmount(totalAmount)
                .build();
        payment = paymentRepository.saveAndFlush(payment);
        updateRoom(createPaymentRequest, room);
        createHistoryRoom(room);
        return new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getTotalAmount());
    }

    private PaymentResponse getPaymentResponse(String roomId) {
        Optional<Payment> payment = paymentRepository.findPaymentByRoomId(UUID.fromString(roomId));
        return payment.map(value -> new PaymentResponse(value.getId().toString(), value.getPaymentStatus(), value.getTotalAmount()))
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    private void updateRoom(CreatePaymentRequest createPaymentRequest, Room room) {
        room.setRoomRate(createPaymentRequest.getRoomRate());
        room.setWaterMeterOldNumber(createPaymentRequest.getWaterMeterNewNumber());
        room.setElectricMeterOldNumber(createPaymentRequest.getElectricityMeterNewNumber());
        roomRepository.save(room);
    }

    private void createHistoryRoom(Room room) {
        HistoryRoom historyRoom = HistoryRoom.builder()
                .room(room)
                .electricMeterNumber(room.getElectricMeterOldNumber())
                .waterMeterNumber(room.getWaterMeterOldNumber())
                .isDelete(false)
                .build();
        historyRoomRepository.save(historyRoom);
    }

    @Override
    @Transactional
    public PaymentResponse updatePaymentStatus(UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        Payment payment = paymentRepository.findById(UUID.fromString(updatePaymentStatusRequest.getId()))
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
        payment.setPaymentStatus(PaymentStatus.valueOf(updatePaymentStatusRequest.getStatus().toUpperCase()));

        payment = paymentRepository.saveAndFlush(payment);
        return new PaymentResponse(payment.getId().toString(), payment.getPaymentStatus(), payment.getTotalAmount());
    }

    @Override
    public Page<RoomResponse> getListRooms(PagingRequest pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<Room> pageRoom = roomRepository.getAllRooms(pageable);
        List<RoomResponse> roomResponses = pageRoom.stream()
                .map(room -> roomMapper.from(room, getPaymentResponse(room.getId().toString()))).toList();
        return new PageImpl<>(roomResponses, pageable, pageRoom.getTotalElements());
    }

    private int calTotalAmount(CreatePaymentRequest createPaymentRequest, RoomSetting roomSetting, Room room) {
        if (ObjectUtils.isEmpty(roomSetting)) {
            throw new BaseException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        return createPaymentRequest.getRoomRate()
                + (createPaymentRequest.getElectricityMeterNewNumber() - room.getElectricMeterOldNumber()) * roomSetting.getElectricBill()
                + (createPaymentRequest.getWaterMeterNewNumber() - room.getWaterMeterOldNumber()) * roomSetting.getWaterBill();
    }
    @Override
    public AddPeopleResponse addPeopleToRoom(AddPeopleRequest request) {
        Room room = roomRepository.findById(UUID.fromString(request.getRoomId()))
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        List<AddPeopleResponse.UserResponse> userResponses = new ArrayList<>();

        for (AddPeopleRequest.PeopleRequest person : request.getPeople()) {
            User user = userRepository.findByEmail(person.getEmail())
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
            boolean isAlreadyInRoom = roomUserRepository.existsByRoomAndUser(room, user);
            if (isAlreadyInRoom) {
                throw new BaseException(ErrorCode.USER_ALREADY_IN_ROOM);
            }
            RoomUser roomUser = new RoomUser();
            roomUser.setRoom(room);
            roomUser.setUser(user);
            roomUserRepository.save(roomUser);
            userResponses.add(new AddPeopleResponse.UserResponse(user.getPhoneNumber(), user.getEmail()));
        }
        return new AddPeopleResponse(UUID.fromString(request.getRoomId()), userResponses);
    }
    @Override
    public UpdatePeopleResponse updatePeopleInRoom(UpdatePeopleRequest request) {
        Room room = roomRepository.findById(UUID.fromString(request.getRoomId()))
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        List<UpdatePeopleResponse.PeopleResponse> userResponses = new ArrayList<>();
        for (UpdatePeopleRequest.PeopleRequest person : request.getPeople()) {
            User user = userRepository.findByEmail(person.getEmail())
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
            RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user)
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
            user.setPhoneNumber(person.getPhoneNumber());
            userRepository.save(user);
            userResponses.add(new UpdatePeopleResponse.PeopleResponse(user.getPhoneNumber(), user.getEmail()));
        }
        return new UpdatePeopleResponse(UUID.fromString(request.getRoomId()), userResponses);
    }
    @Override
    public void removePeopleFromRoom(DeletePeopleRequest request) {
        Room room = roomRepository.findById(UUID.fromString(request.getRoomId()))
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));
        for (String email : request.getEmail()) {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
            RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user)
                    .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
            roomUserRepository.delete(roomUser);
        }
    }
    @Override
    public RoomDetailResponse getRoomDetail(String roomId) {
        Room room = roomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new BaseException(ErrorCode.ROOM_NOT_FOUND));

        List<RoomDetailResponse.UserResponse> userResponses = roomUserRepository.findByRoom(room)
                .stream()
                .map(roomUser -> new RoomDetailResponse.UserResponse(roomUser.getUser().getFirstName() + " " + roomUser.getUser().getLastName(), roomUser.getUser().getEmail()))
                .collect(Collectors.toList());

        return new RoomDetailResponse(
                room.getRoomName(),
                room.getRoomRate(),
                room.getElectricMeterOldNumber(),
                room.getWaterMeterOldNumber(),
                room.getRoomStatus(),
                room.isDelete(),
                userResponses
        );
    }
}

