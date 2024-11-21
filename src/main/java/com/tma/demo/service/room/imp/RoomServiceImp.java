package com.tma.demo.service.room.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.PaymentStatus;
import com.tma.demo.common.RoomStatus;
import com.tma.demo.dto.request.*;
import com.tma.demo.dto.response.*;
import com.tma.demo.entity.*;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.*;
import com.tma.demo.filter.RoomFilter;
import com.tma.demo.repository.HistoryRoomRepository;
import com.tma.demo.repository.PaymentRepository;
import com.tma.demo.repository.RoomRepository;
import com.tma.demo.service.boarding_house.BoardingHouseService;
import com.tma.demo.service.room.RoomService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    private final Mapper mapper;
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
        PaymentResponse paymentResponse = createPayment(new CreatePaymentRequest(room.getId().toString(), room.getRoomRate(), room.getElectricMeterOldNumber(), room.getWaterMeterOldNumber()), PaymentStatus.PAID);
        return mapper.from(room, paymentResponse);
    }

    @Override
    @Transactional
    public RoomResponse resetRoom(String roomId) {
        Room room = getRoomById(roomId);
        checkAth(room.getBoardingHouse().getUser().getId().toString());
        room.setRoomRate(0);
        room.setRoomStatus(RoomStatus.ROOM_AVAILABLE);
        List<RoomUser> listRoomUser = getRoomUser(room.getId());
        roomUserRepository.deleteAll(listRoomUser);
        Payment payment = Payment.builder()
                .totalAmount(0)
                .room(room)
                .waterMeterNewNumber(room.getWaterMeterOldNumber())
                .electricityMeterNewNumber(room.getElectricMeterOldNumber())
                .paymentStatus(PaymentStatus.PAID)
                .build();
        payment = paymentRepository.saveAndFlush(payment);
        PaymentResponse paymentResponse = mapper.from(payment);
        return mapper.from(roomRepository.saveAndFlush(room), paymentResponse);
    }

    @Override
    public RoomResponse updateRoomStatus(UpdateRoomStatusRequest request) {
        Room room = getRoomById(request.getId());
        room.setRoomStatus(RoomStatus.valueOf(request.getStatus().toUpperCase()));
        room = roomRepository.saveAndFlush(room);
        return mapper.from(room, getPaymentResponse(request.getId()));
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
        if (isRoomNameExist(request.getRoomName(), UUID.fromString(request.getBoardingHouseId()))) {
            throw new BaseException(ErrorCode.ROOM_NAME_ALREADY_EXIST);
        }
    }

    private boolean isRoomNameExist(String roomName, UUID id) {
        return roomRepository.isRoomNameExist(roomName, id) > 0;
    }

    private void checkAth(String userId) {
        if (!userId.equals(userService.getUserDetails().getId().toString())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
    }

    private List<RoomUser> getRoomUser(UUID roomId){
        return roomUserRepository.findByRoomId(roomId);
    }

    //PAYMENT
    @Override
    @Transactional
    public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest, PaymentStatus paymentStatus) {
        Room room = getRoomById(createPaymentRequest.getRoomId());
        RoomSetting roomSetting = boardingHouseService.getSetting(room.getBoardingHouse().getId().toString());
        int totalAmount = calTotalAmount(createPaymentRequest, roomSetting, room);
        Payment payment = Payment.builder()
                .paymentStatus(paymentStatus)
                .electricityMeterNewNumber(createPaymentRequest.getElectricityMeterNewNumber())
                .waterMeterNewNumber(createPaymentRequest.getWaterMeterNewNumber())
                .room(room)
                .totalAmount(totalAmount)
                .build();
        payment = paymentRepository.saveAndFlush(payment);
        room.setRoomRate(createPaymentRequest.getRoomRate());
        roomRepository.save(room);
        return mapper.from(payment);
    }

    private PaymentResponse getPaymentResponse(String roomId) {
        Optional<Payment> payment = paymentRepository.findPaymentByRoomId(UUID.fromString(roomId));
        return payment.map(mapper::from)
                .orElseThrow(() -> new BaseException(ErrorCode.PAYMENT_NOT_FOUND));
    }

    private void updateRoom(Payment payment) {
        Room room = payment.getRoom();
        room.setWaterMeterOldNumber(payment.getWaterMeterNewNumber());
        room.setElectricMeterOldNumber(payment.getElectricityMeterNewNumber());
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
        updateRoom(payment);
        createHistoryRoom(payment.getRoom());
        return mapper.from(payment);
    }

    @Override
    public Page<RoomResponse> getListRooms(PagingRequest<RoomFilter> pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<Room> pageRoom;
        PaymentStatus status = ObjectUtils.isEmpty(pagingRequest.getFilter().getPaymentStatus()) ? null : PaymentStatus.valueOf(pagingRequest.getFilter().getPaymentStatus().toUpperCase());
        RoomStatus roomStatus = ObjectUtils.isEmpty(pagingRequest.getFilter().getRoomStatus()) ? null : RoomStatus.valueOf(pagingRequest.getFilter().getRoomStatus().toUpperCase());
        String date = ObjectUtils.isEmpty(pagingRequest.getFilter().getDate()) ? "" : pagingRequest.getFilter().getDate();
        pageRoom = roomRepository.getAllRooms(pageable,
                UUID.fromString(pagingRequest.getFilter().getBoardingHouseId()), status, roomStatus, date);
        List<RoomResponse> roomResponses = pageRoom.stream()
                .map(room -> mapper.from(room, getPaymentResponse(room.getId().toString()))).toList();
        return new PageImpl<>(roomResponses, pageable, pageRoom.getTotalElements());
    }

    private int calTotalAmount(CreatePaymentRequest createPaymentRequest, RoomSetting roomSetting, Room room) {
        if (ObjectUtils.isEmpty(roomSetting)) {
            throw new BaseException(ErrorCode.ROOM_SETTING_NOT_FOUND);
        }
        return createPaymentRequest.getRoomRate()
                + (createPaymentRequest.getElectricityMeterNewNumber() - room.getElectricMeterOldNumber()) * roomSetting.getElectricBill()
                + (createPaymentRequest.getWaterMeterNewNumber() - room.getWaterMeterOldNumber()) * roomSetting.getWaterBill();
    }

    @Override
    public PeopleResponse addPeopleToRoom(PeopleRequest request) {
        Room room = getRoomById(request.getRoomId());
        List<UserReponseRoom> userResponses = new ArrayList<>();
        for (ListPeopleContext person : request.getPeople()) {
            RoomUser roomUser = new RoomUser();
            roomUser.setFullName(person.getFullName());
            roomUser.setPhoneNumber(person.getPhoneNumber());
            roomUser.setRoom(room);
            roomUserRepository.save(roomUser);
            userResponses.add(new UserReponseRoom(roomUser.getId(),roomUser.getFullName(),roomUser.getPhoneNumber()));
        }
        return new PeopleResponse(UUID.fromString(request.getRoomId()), userResponses);
    }

    @Override
    public PeopleResponse updatePeopleInRoom(PeopleRequest peopleRequest) {
        RoomUser roomUser = checkRoomUserById(peopleRequest.getRoomUserId());
        List<UserReponseRoom> userResponses = new ArrayList<>();
        for (ListPeopleContext person : peopleRequest.getPeople()) {
            roomUser.setFullName(person.getFullName());
            roomUser.setPhoneNumber(person.getPhoneNumber());
            roomUserRepository.save(roomUser);
            userResponses.add(new UserReponseRoom(roomUser.getId(),roomUser.getFullName(),roomUser.getPhoneNumber()));
        }
        return new PeopleResponse(null,userResponses);
    }

    @Override
    public void removePeopleFromRoom(String roomUserId) {
        RoomUser roomUser = checkRoomUserById(roomUserId);
            roomUser.setDelete(true);
            roomUserRepository.save(roomUser);
    }

    @Override
    public RoomDetailResponse getRoomDetail(String roomId) {
        Room room = getRoomById(roomId);
        Payment getNewPayment = paymentRepository.findByRoomId(room.getId())
                .stream()
                .findFirst()
                .orElse(null);
//        Get Old Payment
        List<UserReponseRoom> userResponses = roomUserRepository.findByRoomAndIsDeleteFalse(room)
                .stream()
                .map(roomUser -> new UserReponseRoom(
                        roomUser.getId(),
                        roomUser.getFullName(),
                        roomUser.getPhoneNumber()

                ))
                .collect(Collectors.toList());

        return new RoomDetailResponse(
                room.getRoomName(),
                room.getRoomRate(),
                room.getElectricMeterOldNumber(),
                room.getWaterMeterOldNumber(),
                getNewPayment != null ? getNewPayment.getElectricityMeterNewNumber() : null,
                getNewPayment != null ? getNewPayment.getWaterMeterNewNumber() : null,
                userResponses
        );
    }

    private RoomUser checkRoomUserById(String id) {
        return roomUserRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
    }

}
