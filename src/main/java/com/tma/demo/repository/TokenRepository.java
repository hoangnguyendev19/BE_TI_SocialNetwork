package com.tma.demo.repository;

import com.tma.demo.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
public interface TokenRepository extends JpaRepository<Token, UUID> {
    @Query("SELECT t FROM Token  t where t.accessToken = :token and t.isRevoked != true")
    Optional<Token> findByAccessToken(@Param("token") String token);
}
