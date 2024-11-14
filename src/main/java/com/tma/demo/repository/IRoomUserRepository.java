package com.tma.demo.repository;

import com.tma.demo.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * IRoomUserRepository
 * Version 1.0
 * Date: 29/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 29/10/2024        NGUYEN             create
 */
public interface IRoomUserRepository extends JpaRepository<RoomUser, UUID> {
}
