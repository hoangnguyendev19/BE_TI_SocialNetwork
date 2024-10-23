package com.tma.demo.service.post;

import com.tma.demo.dto.request.CreatePostRequest;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.request.UpdatePostRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest);

    PostDto updatePost(UpdatePostRequest updatePostRequest);

    void deletePost(String postId);

    Page<PostDto> getNews(PagingRequest pagingRequest);

    Post getPost(String postId);

    PostDto getPostDto(String postId);
}
