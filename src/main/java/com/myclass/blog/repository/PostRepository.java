package com.myclass.blog.repository;

import com.myclass.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
