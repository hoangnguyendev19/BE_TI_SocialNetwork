package com.tma.demo.service.post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.MediaType;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.FolderNameConstant;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Media;
import com.tma.demo.entity.Post;
import com.tma.demo.entity.User;
import com.tma.demo.exception.BaseException;
import com.tma.demo.repository.MediaRepository;
import com.tma.demo.repository.PostRepository;
import com.tma.demo.repository.UserRepository;
import com.tma.demo.service.cloudinary.CloudinaryService;
import com.tma.demo.service.post.PostMapper;
import com.tma.demo.service.post.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * PostServiceImp
 * Version 1.0
 * Date: 11/10/2024
 * Copyright
 * Modification Logs
 * DATE          AUTHOR          DESCRIPTION
 * ------------------------------------------------
 * 11/10/2024        NGUYEN             create
 */
@Data
@RequiredArgsConstructor
@Service
public class PostServiceImp implements PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CloudinaryService cloudinaryService;
    private final MediaRepository mediaRepository;
    private final PostMapper postMapper;

    // POST
    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public PostDto createPost(String content, MultipartFile[] mediaFiles) {
        User user = getUser();
        Post post = Post.builder()
                .content(content)
                .user(user)
                .isDelete(false)
                .parentPost(null)
                .totalShares(0)
                .build();
        post = postRepository.save(post);
        List<Media> mediaList = saveAllMediaFiles(mediaFiles, post);
        return postMapper.from(post, mediaList);
    }

    //    =================================================================================================================
//    MEDIA
    private List<Media> saveAllMediaFiles(MultipartFile[] mediaFiles, Post post) {
        List<Media> mediaList = new ArrayList<>();
        for (MultipartFile mediaFile : mediaFiles) {
            String uuid = UUID.randomUUID().toString();
            Map data = cloudinaryService.upload(mediaFile, FolderNameConstant.POST, post.getId() + "/" + uuid);
            Media media = Media.builder()
                    .isDelete(false)
                    .mediaUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString())
                    .mediaType(MediaType.IMAGE)
                    .post(post)
                    .build();
            mediaList.add(mediaRepository.saveAndFlush(media));
        }
        return mediaList;
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED);
        }
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_DOES_NOT_EXIST));
    }
}
