package com.tma.demo.repository;

import com.tma.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * IUserRepository
 * Version 1.0
 * Date: 07/10/2024
 * Copyright
 * Modification Logs
 * DATE AUTHOR DESCRIPTION
 * ------------------------------------------------
 * 07/10/2024 NGUYEN create
 */
public interface IUserRepository extends JpaRepository<User, UUID> {

}
