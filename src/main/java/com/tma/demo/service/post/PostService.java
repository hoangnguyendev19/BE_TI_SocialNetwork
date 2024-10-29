package com.tma.demo.service.post;

import com.tma.demo.dto.request.CreatePostRequest;
import com.tma.demo.dto.request.PagingRequest;
import com.tma.demo.dto.request.UpdatePostRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.dto.response.UserResponse;
import com.tma.demo.entity.Post;
import com.tma.demo.filter.IdFilter;
import org.springframework.data.domain.Page;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest);

    PostDto updatePost(UpdatePostRequest updatePostRequest);

    void deletePost(String postId);

    Page<PostDto> getNews(PagingRequest pagingRequest);

    Post getPost(String postId);

    PostDto getPostDto(String postId);

    Page<UserResponse> getSharedList(PagingRequest<IdFilter> pagingRequest);
}
