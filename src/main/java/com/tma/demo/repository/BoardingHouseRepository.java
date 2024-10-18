package com.tma.demo.repository;

import com.tma.demo.entity.BoardingHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BoardingHouseRepository extends JpaRepository<BoardingHouse, UUID> {

    @Query("SELECT b FROM BoardingHouse b WHERE b.id = :id AND b.isDelete != true ")
    BoardingHouse findBoardingHouseById(@Param("id") UUID id);

    @Query("SELECT exists (SELECT b FROM BoardingHouse b WHERE b.boardingHouseName = :name and b.isDelete != true )")
    boolean isBoardingHouseNameExists(@Param("name") String name);
}
