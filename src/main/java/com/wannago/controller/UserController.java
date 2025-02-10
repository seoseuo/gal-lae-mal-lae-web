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

@Log4j2
@RestController
@RequestMapping("/users/me")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 내 정보 조회
    @GetMapping
    public ResponseEntity<UserDTO> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok(userService.findByUsIdx(userDTO.getUsIdx()));
    }

    // 이름 수정
    @PatchMapping("/name")
    public ResponseEntity<String> updateName(@RequestBody Map<String, String> request) {
        String usName = request.get("usName");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();        
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        userDTO.setUsName(usName);
        log.info("updateDTO : {}", userDTO);
        userService.updateUsNameByUsIdx(userDTO.getUsIdx(), usName);
        return ResponseEntity.ok("Name updated successfully");
    }

    //
    // // 비밀번호 변경
    // @PatchMapping("/me/password")
    // public ResponseEntity<String> updatePassword(@RequestParam String
    // currentPassword, @RequestParam String newPassword) {
    // userService.updatePassword(currentPassword, newPassword);
    // return ResponseEntity.ok("Password updated successfully");
    // }
    //
    // // 다녀온 여행지 목록 조회
    // @GetMapping("/me/travel-history")
    // public List<String> getTravelHistory() {
    // return userService.getTravelHistory();
    // }
    //
    // // 팔로우 목록 조회
    // @GetMapping("/me/follow-list")
    // public List<String> getFollowList() {
    // return userService.getFollowList();
    // }
    // }

}
