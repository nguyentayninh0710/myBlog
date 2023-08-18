package com.myclass.blog.service;

import com.myclass.blog.payload.PostDto;
import com.myclass.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostbyId(long id);

    PostDto updatePost (PostDto postDto, long id);

    void deletePostById(long id);
}
