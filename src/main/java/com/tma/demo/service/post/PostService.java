package com.tma.demo.service.post;

import com.tma.demo.dto.request.ReportPostRequest;
import com.tma.demo.dto.request.CreatePostRequest;
import com.tma.demo.dto.request.UpdatePostRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Post;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Service
public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest);

    PostDto updatePost(UpdatePostRequest updatePostRequest);

    Post getPost(String postId);

    void deletePost(String postId);

    Page<PostDto> getNews(Pageable pageable);

}
