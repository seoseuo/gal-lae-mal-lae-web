package com.wannago.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import com.wannago.dto.BoardDTO;
import com.wannago.dto.BoardSearchDTO;
import com.wannago.dto.LocationDoDTO;
import com.wannago.dto.LocationSiDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.service.TravelGroupService;
import com.wannago.util.jwt.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import com.wannago.service.MateService;

@Controller
@RequestMapping("/mate")
public class MateController {
    @Autowired
    private TravelGroupService travelGroupService;

    @Autowired
    private MateService mateService;

    @Autowired
    private JwtProvider jwtProvider;

    //도 정보가져오기
    @GetMapping("/location/do")
    public ResponseEntity<List<LocationDoDTO>> getLocationDoList() {
        return ResponseEntity.ok(travelGroupService.getLocationDoList());
    }

    //시 정보가져오기
    @GetMapping("/location/si/{ldIdx}")
    public ResponseEntity<List<LocationSiDTO>> getLocationSiList(@PathVariable("ldIdx") int ldIdx) {
        return ResponseEntity.ok(travelGroupService.getLocationSiList(ldIdx));
    }

    @PostMapping("/board")
    public ResponseEntity<ResponseDTO> createBoard(@RequestBody BoardDTO boardDTO, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }

        if(token == null){
            return ResponseEntity.ok(new ResponseDTO(false, "로그인이 필요합니다."));
        }
        int usIdx = jwtProvider.getAccessTokenClaimsFromToken(token).getUsIdx();
        boardDTO.setUsIdx(usIdx);
        return ResponseEntity.ok(mateService.createBoard(boardDTO));
    }

    @GetMapping("/board")
    public ResponseEntity<Page<BoardDTO>> getBoardList(
        @RequestParam(name = "location", required = false) Integer location,
        @RequestParam(name = "search", required = false) String search,
        @RequestParam(name = "sort", defaultValue = "lastest") String sort,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        BoardSearchDTO boardSearchDTO = new BoardSearchDTO(search, sort, location);
        return ResponseEntity.ok(mateService.getBoardList(boardSearchDTO, pageable));
    }

    @GetMapping("/board/{boIdx}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable("boIdx") int boIdx){
        return ResponseEntity.ok(mateService.getBoard(boIdx));
    }

    @PutMapping("/board/{boIdx}/like")
    public ResponseEntity<Integer> likeBoard(@PathVariable("boIdx") int boIdx){
        return ResponseEntity.ok(mateService.likeBoard(boIdx));
    }
}
