package com.tma.demo.repository;

import com.tma.demo.entity.HistoryRoom;
import com.tma.demo.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * PaymentRepository
 * Version 1.0
 * Date: 22/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 22/10/2024        NGUYEN             create
 */
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    @Query("SELECT p FROM Payment p WHERE p.room.id = :id ORDER BY p.createdAt DESC limit 1")
    Optional<Payment> findPaymentByRoomId(@Param("id") UUID roomId);
    @Query(value = "SELECT p FROM Payment p WHERE p.room.id = :roomId ORDER BY p.createdAt DESC")
    List<Payment> findByRoomId(@Param("roomId") UUID roomId);
}
