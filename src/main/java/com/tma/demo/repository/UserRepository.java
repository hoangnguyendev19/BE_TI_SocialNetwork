package com.tma.demo.repository;

import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * UserRepository
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE AUTHOR DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024 NGUYEN create
 */
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE u.email = :email and u.isDelete != true")
    Optional<User> findByEmail(@Param("email") String email);
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
}
