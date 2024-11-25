package com.tma.demo.repository;

import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IPostRepository extends JpaRepository<Post, UUID> {

}
