package com.wannago.repository;

import com.wannago.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.wannago.dto.PostDTO;
public interface PostRepository extends JpaRepository<Post, Integer> {
    // 게시글 목록 조회
    List<Post> findAll();
    
    // 게시글 작성
    Optional<Post> save(PostDTO postDTO);
}