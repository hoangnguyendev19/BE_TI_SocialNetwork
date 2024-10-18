package com.tma.demo.service.favourite.imp;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.entity.Like;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.repository.FavouriteRepository;
import com.tma.demo.service.favourite.FavouriteService;
import com.tma.demo.service.post.PostService;
import com.tma.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public LikeDto createFavouritePost(LikeDto likeDto) {
        User user = userService.getUserDetails();
        Post post = postService.getPost(likeDto.getPostId());
        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        favouriteRepository.save(like);
        return new LikeDto(like.getPost().getId().toString());
    }
}
