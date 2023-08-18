package com.myclass.blog.service;

import com.myclass.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(long id);
}
