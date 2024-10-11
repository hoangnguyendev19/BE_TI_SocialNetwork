package com.tma.demo.service.post;

import com.tma.demo.dto.response.MediaDto;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Media;
import com.tma.demo.entity.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * PostMapper
 * Version 1.0
 * Date: 11/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/10/2024        NGUYEN             create
 */
@Service
public class PostMapper {
    public PostDto from(Post post, List<Media> mediaList, PostDto parentPost) {
        List<MediaDto> mediaDtoList = new ArrayList<>();
        for (Media media : mediaList) {
            mediaDtoList.add(new MediaDto(media.getId().toString(), media.getMediaUrl()));
        }
        return PostDto.builder()
                .id(post.getId().toString())
                .content(post.getContent())
                .parentPost(parentPost)
                .mediaList(mediaDtoList)
                .build();
    }
}
