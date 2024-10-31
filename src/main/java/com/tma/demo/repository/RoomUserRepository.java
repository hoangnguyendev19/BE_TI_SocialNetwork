package com.tma.demo.repository;

import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomUserRepository extends JpaRepository<RoomUser, UUID> {
    boolean existsByFullName(String fullName);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<RoomUser> findByRoomAndFullName(Room room, String fullName);
    Optional<RoomUser> findByRoomAndPhoneNumber(Room room, String phoneNumber);
    List<RoomUser> findByRoom(Room room);
    List<RoomUser> findByRoomAndIsDeleteFalse(Room room);

}
