package com.tma.demo.service.favourite;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.LikeResponse;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.filter.PostFilter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FavouriteService {
    LikeDto createFavouritePost(LikeDto likeDto);

    void deleteFavouritePost(String id);

    Page<LikeResponse> getFavouritePosts(PagingRequest<PostFilter> pagingRequest);
}
