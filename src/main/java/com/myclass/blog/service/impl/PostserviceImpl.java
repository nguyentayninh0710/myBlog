package com.myclass.blog.service.impl;

import com.myclass.blog.entity.Category;
import com.myclass.blog.entity.Post;
import com.myclass.blog.exception.ResourceNotFoundException;
import com.myclass.blog.payload.PostDto;
import com.myclass.blog.payload.PostResponse;
import com.myclass.blog.repository.CategoryRepository;
import com.myclass.blog.repository.PostRepository;
import com.myclass.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostserviceImpl implements PostService {

    private PostRepository postRepository;

    private  ModelMapper mapper;

    private CategoryRepository categotyRepository;


    public PostserviceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categotyRepository) {

        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categotyRepository = categotyRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categotyRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        //Consert Dto -> entity
        Post post = mapToEntiry(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        //Convert Entity -> Dto
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts =  postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content =  listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostbyId(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        // get post by id
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Category category = categotyRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setCategory(category);
        Post updatePost = postRepository.save(post);
        return mapToDto(updatePost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categotyRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map((post) -> mapToDto(post)).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);
        return postDto;
    }

    private Post mapToEntiry(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
        return post;
    }
}
