package com.wannago.controller;

import com.wannago.service.UserService;
import com.wannago.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.log4j.Log4j2;
import java.util.Map;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/users/me")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // authentication 에서 UserDTO 추출
    private UserDTO getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User from authentication: {}", authentication);
        return (UserDTO) authentication.getPrincipal();
    }

    // 내 정보 조회
    @GetMapping
    public ResponseEntity<UserDTO> getMyInfo() {
        log.info("/users/me 호출");
        return ResponseEntity.ok(userService.findByUsIdx(getUserFromAuthentication().getUsIdx()));
    }

    // 이름 수정
    @PatchMapping("/name")
    public ResponseEntity<String> updateName(@RequestBody UserDTO newUserDTO) {
        log.info("/users/me/name 호출");
        log.info("usName : {}", newUserDTO.getUsName());
        userService.updateUsNameByUsIdx(getUserFromAuthentication().getUsIdx(), newUserDTO.getUsName());
        return ResponseEntity.ok("이름 수정에 성공하였습니다.");
    }

    // 이름 중복 체크
    @GetMapping("/check-name")
    public ResponseEntity<String> chackName(@RequestParam("usName") String usName) {
        log.info("/users/me/chack-name 호출");
        log.info("usName : {}", usName);
        // Ture일 시-> 이미 존재하는 회원, 즉 수정이 불가하다.
        // False일 시-> 존재하지 않는 회원, 즉 수정이 가능하다.
        // 중복 체크 후 수정 가능 여부 반환
        if (userService.chackName(usName)) {
            return ResponseEntity.ok("중복된 이름입니다.");
        } else {
            return ResponseEntity.ok("수정이 가능합니다.");
        }
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestBody UserDTO newUserDTO) {
        log.info("/users/me/password 호출");
        log.info("newPassword : {}", newUserDTO.getUsPw());
        userService.updateUsPwByUsIdx(getUserFromAuthentication().getUsIdx(), newUserDTO.getUsPw());
        return ResponseEntity.ok("비밀번호 수정에 성공하였습니다.");
    }

    // 팔로우 목록 조회
    @GetMapping("/follow-list")
    public ResponseEntity<Map<String, List<UserDTO>>> getFollowList() {
        log.info("/users/me/follow-list 호출");
        Map<String, List<UserDTO>> followList = userService.getFollowList(getUserFromAuthentication().getUsIdx());
        log.info("followList : {}", followList);
        return ResponseEntity.ok(followList);
    }
}

//
// // 다녀온 여행지 목록 조회
// @GetMapping("/me/travel-history")
// public List<String> getTravelHistory() {
// return userService.getTravelHistory();
// }
//
