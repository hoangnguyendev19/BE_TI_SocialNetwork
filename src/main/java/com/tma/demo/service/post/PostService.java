package com.tma.demo.service.post;

import com.tma.demo.dto.response.PostDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PostService {
    PostDto createPost(String content, MultipartFile[] mediaFiles);
}
