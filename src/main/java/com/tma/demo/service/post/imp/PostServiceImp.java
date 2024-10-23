package com.tma.demo.service.post.imp;

import com.tma.demo.common.ErrorCode;
import com.tma.demo.common.MediaType;
import com.tma.demo.constant.AttributeConstant;
import com.tma.demo.constant.FolderNameConstant;
import com.tma.demo.constant.FormatConstant;
import com.tma.demo.dto.request.CreatePostRequest;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.request.UpdatePostRequest;
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
import com.tma.demo.service.user.UserService;
import com.tma.demo.util.PageUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

import static com.tma.demo.constant.CommonConstant.EMPTY_STRING;
import static com.tma.demo.constant.CommonConstant.OLIDUS;
import static com.tma.demo.constant.PrefixConstant.BASE64_DATA_TYPE_PREF;
import static com.tma.demo.constant.PrefixConstant.BASE64_PREF;

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
    private final UserService userService;

    // POST
    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public PostDto createPost(CreatePostRequest createPostRequest) {
        User user = userService.getUserDetails();
        Post parentPost = null;
        if (!ObjectUtils.isEmpty(createPostRequest.getParentPostId())) {
            parentPost = getParentPost(postRepository.findById(UUID.fromString(createPostRequest.getParentPostId()))
                    .orElse(null));
        }
        Post post = Post.builder()
                .content(createPostRequest.getContent())
                .user(user)
                .isDelete(false)
                .parentPost(parentPost)
                .build();
        post = postRepository.saveAndFlush(post);
        List<Media> mediaList = saveAllMediaFiles(createPostRequest.getFiles(), post);
        return getPostDto(post.getId().toString());
    }

    @Override
    public PostDto updatePost(UpdatePostRequest updatePostRequest) {
        Post post = getPost(updatePostRequest.getPostId());
        if (post.getUser().getId() != userService.getUserDetails().getId()) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        post.setContent(updatePostRequest.getContent());
        saveAllMediaFiles(updatePostRequest.getFiles(), post);
        List<UUID> deletedFileIds = updatePostRequest.getDeleteFileIds().stream().map(UUID::fromString).toList();
        deleteMedia(deletedFileIds, UUID.fromString(updatePostRequest.getPostId()));
        deleteMediaInCloud(updatePostRequest.getDeleteFileIds(), updatePostRequest.getPostId(), FolderNameConstant.POST);
        post = postRepository.saveAndFlush(post);
        return getPostDto(post.getId().toString());
    }

    @Override
    public Page<PostDto> getNews(PagingRequest pagingRequest) {
        Pageable pageable = PageUtil.getPageRequest(pagingRequest);
        Page<Post> posts = postRepository.getNews(pageable);
        List<PostDto> postsDto = posts.stream().map(
                post -> getPostDto(post.getId().toString())
        ).toList();
        return new PageImpl<>(postsDto, pageable, posts.getTotalElements());
    }

    @Override
    public PostDto getPostDto(String postId) {
        User user = userService.getUserDetails();
        Post post = getPost(postId);
        List<Media> mediaList = getMediaByPostId(post.getId());
        PostDto parentDto = ObjectUtils.isEmpty(post.getParentPost()) ? null :
                postMapper.from(post.getParentPost(), getMediaByPostId(post.getParentPost().getId()), null, user);
        return postMapper.from(post, mediaList, parentDto, user);
    }

    @Override
    public Post getPost(String postId) {
        return postRepository.findPostById(UUID.fromString(postId))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));
    }

    private Post getParentPost(Post post) {
        if (ObjectUtils.isEmpty(post)) {
            return null;
        }
        if (ObjectUtils.isEmpty(post.getParentPost())) {
            return post;
        }
        return getParentPost(post.getParentPost());
    }

    @Override
    public void deletePost(String postId) {
        Post post = postRepository.findPostById(UUID.fromString(postId))
                .orElseThrow(() -> new BaseException(ErrorCode.POST_DOES_NOT_EXIST));
        if (!post.getUser().getId().equals(userService.getUserDetails().getId())) {
            throw new BaseException(ErrorCode.UNAUTHORIZED);
        }
        post.setDelete(true);
        postRepository.save(post);
    }

    private List<Media> saveAllMediaFiles(List<String> mediaFiles, Post post) {
        List<Media> mediaList = new ArrayList<>();
        for (String mediaFile : mediaFiles) {
            MediaType mediaType = getMediaType(mediaFile);
            int index;
            Media media = Media.builder()
                    .isDelete(false)
                    .mediaType(mediaType)
                    .post(post)
                    .build();
            media = mediaRepository.saveAndFlush(media);
            String fileWithoutHeader = getBase64WithoutHeader(mediaFile);
            byte[] decodedBytes = Base64.getDecoder().decode(fileWithoutHeader);
            Map data = cloudinaryService.upload(
                    decodedBytes, mediaType,
                    FolderNameConstant.POST,
                    String.format(FormatConstant.CLOUDINARY_PUBLIC_ID_SAVE_FORMAT, post.getId(), OLIDUS, media.getId()));
            media.setMediaUrl(data.get(AttributeConstant.CLOUDINARY_URL).toString());
            mediaList.add(mediaRepository.saveAndFlush(media));
        }
        return mediaList;
    }

    private static String getBase64WithoutHeader(String mediaFile) {
        int index;
        index = mediaFile.indexOf(BASE64_PREF);
        if (index < 0) {
            throw new BaseException(ErrorCode.NOT_BASE64_FORMAT);
        }
        index = index + 7;
        return mediaFile.substring(index);
    }

    private static MediaType getMediaType(String mediaFile) {
        int index = mediaFile.indexOf(BASE64_DATA_TYPE_PREF);
        if (index < 0) {
            throw new BaseException(ErrorCode.NOT_BASE64_FORMAT);
        }
        index = index + 5;
        return MediaType.valueOf(mediaFile.substring(index, index + 5).toUpperCase());
    }

    private List<Media> getMediaByPostId(UUID postId) {
        return mediaRepository.findAllByPostId(postId);
    }

    private void deleteMedia(List<UUID> deleteFiles, UUID postId) {
        List<Media> mediaList = mediaRepository.findAllByIdsAndPostId(deleteFiles, postId);
        mediaRepository.deleteAll(mediaList);
    }

    private void deleteMediaInCloud(List<String> deleteFiles, String prefix, String folder) {
        for (String deleteFile : deleteFiles) {
            String publicId = String.format(
                    FormatConstant.CLOUDINARY_PUBLIC_ID_DELETE_FORMAT,
                    folder,
                    prefix != null ? OLIDUS + prefix : EMPTY_STRING,
                    OLIDUS,
                    deleteFile
            );
            cloudinaryService.deleteFile(publicId);
        }
    }
}
