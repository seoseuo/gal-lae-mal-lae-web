package com.wannago.controller;

import com.wannago.service.UserService;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.log4j.Log4j2;
import java.util.Map;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.wannago.util.jwt.AccessTokenClaims;
import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.security.SecurityUtil;
import com.wannago.dto.newPassWordDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private JwtProvider jwtProvider;

    private final UserService userService;
    private final SecurityUtil securityUtil;

    @Autowired
    public UserController(UserService userService, SecurityUtil securityUtil) {
        this.userService = userService;
        this.securityUtil = securityUtil;
    }    

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyInfo() {
        log.info("GET : /users/me 호출");
        return ResponseEntity.ok(userService.findByUsIdx(securityUtil.getUserFromAuthentication().getUsIdx()));
    }

    // 이름 수정
    @PatchMapping("/me/name")
    public ResponseEntity<String> updateName(@RequestBody UserDTO newUserDTO) {
        log.info("PATCH : /users/me/name 호출");
        log.info("usName : {}", newUserDTO.getUsName());
        userService.updateUsNameByUsIdx(securityUtil.getUserFromAuthentication().getUsIdx(), newUserDTO.getUsName());
        return ResponseEntity.ok("이름 수정에 성공하였습니다.");
    }

    // 이름 중복 체크
    @GetMapping("/me/check-name")
    public ResponseEntity<String> chackName(@RequestParam("usName") String usName) {
        log.info("GET : /users/me/chack-name 호출");
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
    @PatchMapping("/me/password")
    public ResponseEntity<ResponseDTO> updatePassword(@RequestBody newPassWordDTO newPassWordDTO) {
        ResponseDTO responseDTO = userService.updateUsPwByUsIdx(securityUtil.getUserFromAuthentication().getUsIdx(), newPassWordDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // 팔로우 목록 조회
    @GetMapping("/me/follow-list")
    public ResponseEntity<Map<String, List<UserDTO>>> getFollowList() {
        log.info("GET : /users/me/follow-list 호출");
        Map<String, List<UserDTO>> followList = userService.getFollowList(securityUtil.getUserFromAuthentication().getUsIdx());
        log.info("followList : {}", followList);
        return ResponseEntity.ok(followList);
    }

    // 프로필 이미지 수정
    // multipart/form-data 형식으로 파일 업로드
    @PatchMapping("/me/profile")
    public ResponseEntity<String> updateProfile(@RequestParam("usProfile") MultipartFile file) {
        log.info("PATCH : /users/me/profile 호출");
        log.info("파일명: {}", file.getOriginalFilename());
        log.info("파일 크기: {} bytes", file.getSize());
        log.info("파일 타입: {}", file.getContentType());

        userService.updateUsProfileByUsIdx(securityUtil.getUserFromAuthentication().getUsIdx(), file);
        
        return ResponseEntity.ok("프로필 이미지 수정에 성공하였습니다.");
    }

    // 유저 프로필 조회 - 유저 번호
    @GetMapping("/{usIdx}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("usIdx") int usIdx) {
        log.info("GET : /users/{} 호출", usIdx);
        return ResponseEntity.ok(userService.findByUsIdx(usIdx));
    }

    // 유저 프로필 조회 - 유저 이메일 (검색)
    @GetMapping("/email/{usEmail}")
    public ResponseEntity<UserDTO> getUserProfileByEmail(@PathVariable("usEmail") String usEmail) {
        log.info("GET : /users/email/{} 호출", usEmail);
        return ResponseEntity.ok(userService.findByUsEmail(usEmail));
    }
}
