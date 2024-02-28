package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNum, int pageSize, String sortBy, String sortDirection);
    PostDto getPost(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);
}
