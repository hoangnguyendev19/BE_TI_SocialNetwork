package com.tma.demo.service.favourite.imp;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.UserResponse;
import com.tma.demo.entity.Like;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.filter.PostFilter;
import com.tma.demo.repository.LikeRepository;
import com.tma.demo.service.favourite.FavouriteService;
import com.tma.demo.service.post.PostService;
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public LikeDto createFavouritePost(LikeDto likeDto) {
        User user = userService.getUserDetails();
        Post post = postService.getPost(likeDto.getPostId());
        Like like = getLikeByUserAndPost(user.getId(), post.getId());
        if (ObjectUtils.isEmpty(like)) {
            like = Like.builder()
                    .user(user)
                    .post(post)
                    .build();
            likeRepository.save(like);
        }
        return new LikeDto(like.getPost().getId().toString());
    }

    @Override
    public void deleteFavouritePost(String postId) {
        User user = userService.getUserDetails();
        Post post = postService.getPost(postId);
        Like like = getLikeByUserAndPost(user.getId(), post.getId());
        if (!ObjectUtils.isEmpty(like)) {
            likeRepository.delete(like);
        }
    }

    @Override
    public Page<UserResponse> getFavouritePosts(PagingRequest<PostFilter> pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<User> pageUser = likeRepository.getUsersByPost(pageable, UUID.fromString(pagingRequest.getFilter().getId()));
        List<UserResponse> userResponses = pageUser.stream().map(user -> new UserResponse(user.getId().toString(), user.getFirstName(), user.getLastName(), user.getProfilePictureUrl()))
        .toList();
        return new PageImpl<>(userResponses, pageable, pageUser.getTotalElements());
    }

    private Like getLikeByUserAndPost(UUID userId, UUID postId) {
        return likeRepository.getLikeByUserAndPost(userId, postId).orElse(null);
    }
}
