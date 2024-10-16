package com.tma.demo.service.post;

import com.tma.demo.dto.request.ReportPostRequest;
import com.tma.demo.dto.response.PostDto;
import com.tma.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {
    PostDto createPost(String content, MultipartFile[] mediaFiles);

    PostDto updatePost(String postId, MultipartFile[] files, String content, String[] deleteFiles);

    Post getPost(String postId);

    void deletePost(String postId);

    Page<PostDto> getNews(int page, int pageSize);

}
