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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.*;

import static com.tma.demo.constant.CommonConstant.EMPTY_STRING;
import static com.tma.demo.constant.CommonConstant.OLIDUS;

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
                .build();
        post = postRepository.save(post);
        List<Media> mediaList = saveAllMediaFiles(mediaFiles, post);
        return postMapper.from(post, mediaList, null);
    }

    @Override
    public PostDto updatePost(String postId,
                              MultipartFile[] files,
                              String content,
                              String[] deleteFiles) {

        Post post = postRepository.findPostById(UUID.fromString(postId))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_NOT_FOUND));
        if(post.getId() != getUser().getId()){
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        post.setContent(content);
        saveAllMediaFiles(files, post);
        List<UUID> deletedFileIds = Arrays.stream(deleteFiles).map(UUID::fromString).toList();
        deleteMedia(deletedFileIds, UUID.fromString(postId));
        deleteMediaInCloud(deleteFiles, postId, FolderNameConstant.POST);
        List<Media> mediaList = getMediaByPostId(UUID.fromString(postId));
        PostDto parentPost = getParentPost(post.getParentPost());
        return postMapper.from(post, mediaList, parentPost);
    }

    @Override
    public Page<PostDto> getNews(int page) {
        Sort sort = Sort.by(AttributeConstant.POST_CREATED_AT).descending();
        Pageable pageable = getPageable(page, sort);
        Page<Post> posts = postRepository.getNews(pageable);
        List<PostDto> postsDto = posts.stream().map(post -> {
            List<Media> mediaList = getMediaByPostId(post.getId());
            PostDto parentPost = getParentPost(post.getParentPost());
            return postMapper.from(post, mediaList, parentPost);
        }).toList();
        return new PageImpl<>(postsDto, pageable, posts.getTotalElements());
    }

    private PostDto getParentPost(Post post) {
        if (post == null) {
            return null;
        }
        if (post.getParentPost() == null) {
            return postMapper.from(post, getMediaByPostId(post.getId()), null);
        }
        return getParentPost(post.getParentPost());
    }

    @Override
    public void deletePost(String postId) {
        Post post = postRepository.findPostById(UUID.fromString(postId))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));
        if(post.getId() != (getUser().getId())){
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        post.setDelete(true);
        postRepository.save(post);
    }

    private List<Media> saveAllMediaFiles(MultipartFile[] mediaFiles, Post post) {
        List<Media> mediaList = new ArrayList<>();
        for (MultipartFile mediaFile : mediaFiles) {

            Media media = Media.builder()
                    .isDelete(false)
                    .mediaType(MediaType.IMAGE)
                    .post(post)
                    .build();
            media = mediaRepository.saveAndFlush(media);
            Map data = cloudinaryService.upload(
                    mediaFile,
                    FolderNameConstant.POST,
                    String.format("%s%s%s", post.getId(), OLIDUS, media.getId()));
            media.setMediaUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString());
            mediaList.add(mediaRepository.saveAndFlush(media));
        }
        return mediaList;
    }

    private List<Media> getMediaByPostId(UUID postId) {
        return mediaRepository.findAllByPostId(postId);
    }

    private void deleteMedia(List<UUID> deleteFiles, UUID postId) {
        List<Media> mediaList = mediaRepository.findAllByIdsAndPostId(deleteFiles, postId);
        System.out.println(mediaList);
        mediaRepository.deleteAll(mediaList);
    }

    private void deleteMediaInCloud(String[] deleteFiles, String prefix, String folder) {
        for (String deleteFile : deleteFiles) {
            String publicId = String.format(
                    "%s%s%s%s",
                    folder,
                    prefix != null ? OLIDUS + prefix : EMPTY_STRING,
                    OLIDUS,
                    deleteFile
            );
            cloudinaryService.deleteFile(publicId);
        }
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

    private Pageable getPageable(int page, Sort sort) {

        return PageRequest.of(page, pageSize, sort);
    }
}
