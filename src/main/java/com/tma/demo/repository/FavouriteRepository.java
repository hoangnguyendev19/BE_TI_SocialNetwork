package com.tma.demo.repository;

import com.tma.demo.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<Like, UUID> {
}
