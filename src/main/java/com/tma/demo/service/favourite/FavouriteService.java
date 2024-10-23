package com.tma.demo.service.favourite;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.UserResponse;
import com.tma.demo.filter.PostFilter;
import org.springframework.data.domain.Page;

public interface FavouriteService {
    LikeDto createFavouritePost(LikeDto likeDto);

    void deleteFavouritePost(String id);

    Page<UserResponse> getFavouritePosts(PagingRequest<PostFilter> pagingRequest);
}
