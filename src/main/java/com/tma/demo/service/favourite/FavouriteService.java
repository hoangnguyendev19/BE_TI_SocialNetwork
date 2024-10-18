package com.tma.demo.service.favourite;

import com.tma.demo.dto.LikeDto;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.response.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {
    LikeDto createFavouritePost(LikeDto likeDto);

    void deleteFavouritePost(LikeDto likeDto);

    Page<PostDto> getFavouritePosts(PagingRequest pagingRequest);
}
