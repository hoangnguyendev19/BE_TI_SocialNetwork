package com.tma.demo.repository;

import com.tma.demo.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITokenRepository extends JpaRepository<Token, UUID> {
}
