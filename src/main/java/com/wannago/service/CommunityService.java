package com.wannago.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.wannago.dto.PostDTO;
import lombok.extern.log4j.Log4j2;
import com.wannago.repository.PostRepository;
import com.wannago.mapper.PostMapper;
import java.util.Optional;
import com.wannago.entity.Post;


@Log4j2
@Service
public class CommunityService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    // 커뮤니티 목록 조회
    public List<PostDTO> getCommunity() {
        return postMapper.toDTOList(postRepository.findAll());
    }

    // 커뮤니티 게시글 작성
    public PostDTO createPost(PostDTO postDTO) {        
        return postMapper.toDTO(postRepository.save(postDTO).get());
    }
}
