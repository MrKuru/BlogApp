package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts(int pageNum, int pageSize);
    PostDto getPost(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePostById(long id);
}
