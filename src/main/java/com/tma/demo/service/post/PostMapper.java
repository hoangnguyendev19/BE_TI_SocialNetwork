package com.tma.demo.service.post;

import com.tma.demo.dto.response.MediaDto;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.dto.response.UserDto;
import com.tma.demo.entity.Media;
import com.tma.demo.entity.Post;
import com.tma.demo.repository.CommentRepository;
import com.tma.demo.repository.LikeRepository;
import com.tma.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
@RequiredArgsConstructor
public class PostMapper {
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public PostDto from(Post post, List<Media> mediaList, PostDto parentPost) {
        List<MediaDto> mediaDtoList = getMediaDtoList(mediaList);
        List<String> totalLikes = likeRepository.getTotalLikes(post.getId());
        long totalComments = commentRepository.getTotalComments(post.getId());
        long totalShares = postRepository.getTotalShares(post.getId());
        return PostDto.builder()
                .id(post.getId().toString())
                .userId(post.getUser().getId().toString())
                .content(post.getContent())
                .firstName(post.getUser().getFirstName())
                .lastName(post.getUser().getLastName())
                .profilePictureUrl(post.getUser().getProfilePictureUrl())
                .likes(totalLikes)
                .totalComments(totalComments)
                .totalShares(totalShares)
                .parentPost(parentPost)
                .mediaList(mediaDtoList)
                .createdAt(post.getCreatedAt())
                .lastModified(post.getLastModified())
                .build();
    }

    private static List<MediaDto> getMediaDtoList(List<Media> mediaList) {
        List<MediaDto> mediaDtoList = new ArrayList<>();
        for (Media media : mediaList) {
            mediaDtoList.add(new MediaDto(media.getId().toString(), media.getMediaType(), media.getMediaUrl()));
        }
        return mediaDtoList;
    }
}
