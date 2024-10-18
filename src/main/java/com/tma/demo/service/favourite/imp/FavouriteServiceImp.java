package com.tma.demo.service.favourite.imp;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Like;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.repository.FavouriteRepository;
import com.tma.demo.repository.LikeRepository;
import com.tma.demo.service.favourite.FavouriteService;
import com.tma.demo.service.post.PostService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * FavouriteServiceImp
 * Version 1.0
 * Date: 18/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 18/10/2024        NGUYEN             create
 */
@Service
@RequiredArgsConstructor
public class FavouriteServiceImp implements FavouriteService {
    private final UserService userService;
    private final PostService postService;
    private final FavouriteRepository favouriteRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public LikeDto createFavouritePost(LikeDto likeDto) {
        User user = userService.getUserDetails();
        Post post = postService.getPost(likeDto.getPostId());
        Like like = getLikeByUserAndPost(user.getId(), post.getId());
        if (like == null) {
            like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            favouriteRepository.save(like);
        }
        return new LikeDto(like.getPost().getId().toString());
    }

    @Override
    public void deleteFavouritePost(LikeDto likeDto) {
        User user = userService.getUserDetails();
        Post post = postService.getPost(likeDto.getPostId());
        Like like = getLikeByUserAndPost(user.getId(), post.getId());
        if (like != null) {
            likeRepository.delete(like);
        }
    }

    @Override
    public Page<PostDto> getFavouritePosts(PagingRequest pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        User user = userService.getUserDetails();
        Page<Like> likes = getLikeByUser(pageable, user.getId());
        List<PostDto> postDtoList = likes.stream().map(
                like -> postService.getPostDto(like.getPost().getId().toString())
        ).toList();
        return new PageImpl<>(postDtoList, pageable, likes.getTotalElements());
    }

    private Page<Like> getLikeByUser(Pageable pageable, UUID userId) {
        return likeRepository.getLikeByUser(pageable, userId);
    }

    private Like getLikeByUserAndPost(UUID userId, UUID postId) {
        return likeRepository.getLikeByUserAndPost(userId, postId).orElse(null);
    }
}
