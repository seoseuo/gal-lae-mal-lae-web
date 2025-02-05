package com.wannago.controller;

import com.wannago.service.UserService;
import com.wannago.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/me")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 내 정보 조회
    @GetMapping
    public UserDTO getMyInfo() {
        return userService.getMyInfo();
    }

    // 닉네임 수정
    @PatchMapping("/name/{num}")
    public ResponseEntity<String> updateNickname(@PathVariable int num, @RequestParam String nickname) {
        userService.updateNickname(num, nickname);
        return ResponseEntity.ok("Nickname updated successfully");
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<String> updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
        userService.updatePassword(currentPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    // 다녀온 여행지 목록 조회
    @GetMapping("/travel-history")
    public List<String> getTravelHistory() {
        return userService.getTravelHistory();
    }

    // 팔로우 목록 조회
    @GetMapping("/follow-list")
    public List<String> getFollowList() {
        return userService.getFollowList();
    }
}

}
