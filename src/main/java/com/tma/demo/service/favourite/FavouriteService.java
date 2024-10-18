package com.tma.demo.service.favourite;

import com.tma.demo.dto.LikeDto;
import org.springframework.stereotype.Service;

@Service
public interface FavouriteService {
    LikeDto createFavouritePost(LikeDto likeDto);
}
