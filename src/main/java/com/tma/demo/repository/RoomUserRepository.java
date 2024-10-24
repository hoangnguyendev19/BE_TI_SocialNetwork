package com.tma.demo.repository;

import com.tma.demo.entity.Room;
import com.tma.demo.entity.RoomUser;
import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomUserRepository extends JpaRepository<RoomUser, UUID> {
    boolean existsByRoomAndUser(Room room, User user);
}