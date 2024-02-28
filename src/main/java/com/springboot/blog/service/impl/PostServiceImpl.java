package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public PostResponse getAllPosts(int pageNum ,int pageSize, String sortBy, String sortDirection) {
        Sort sort = checkSortDirection(sortBy, sortDirection);
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Post> pageList = postRepository.findAll(pageable);
        List<Post> postList = pageList.getContent();
        List<PostDto> postDtoList = postList.stream()
                .map(PostServiceImpl::mapToDto)
                .collect(Collectors.toList());
        return getPostResponse(pageList, postDtoList);
    }

    @Override
    public PostDto getPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(post.getTitle());
        post.setDescription(post.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private static PostDto mapToDto(Post savedPost) {
        PostDto dto = new PostDto();
        dto.setTitle(savedPost.getTitle());
        dto.setContent(savedPost.getContent());
        dto.setDescription(savedPost.getDescription());
        dto.setId(savedPost.getId());
        return dto;
    }

    private static Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
    private static PostResponse getPostResponse(Page<Post> pageList, List<PostDto> postDtoList) {
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(pageList.getNumber());
        postResponse.setPageSize(pageList.getSize());
        postResponse.setTotalElements(pageList.getTotalElements());
        postResponse.setTotalPages(pageList.getTotalPages());
        postResponse.setLast(pageList.isLast());
        return postResponse;
    }

    private static Sort checkSortDirection(String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return sort;
    }
}
