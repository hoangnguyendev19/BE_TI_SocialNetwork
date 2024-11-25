package com.tma.demo.repository;

import com.tma.demo.common.SettingKey;
import com.tma.demo.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISettingRepository extends JpaRepository<Setting, UUID> {
}
