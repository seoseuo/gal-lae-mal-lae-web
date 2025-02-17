package com.wannago.controller;

import com.wannago.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.wannago.dto.PostDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    // 커뮤니티 목록 조회
    @GetMapping
    public ResponseEntity<List<PostDTO>> getCommunity() {
        log.info("GET : /community");
        List<PostDTO> posts = communityService.getCommunity();
        return ResponseEntity.ok(posts);
    }

    // 커뮤니티 게시글 작성
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        log.info("POST : /community");
        PostDTO createdPost = communityService.createPost(postDTO);
        return ResponseEntity.ok(createdPost);
    }
}

