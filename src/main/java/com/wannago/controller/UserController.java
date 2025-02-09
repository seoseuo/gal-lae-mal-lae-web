package com.wannago.controller;

import com.wannago.service.UserService;
import com.wannago.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 내 정보 조회
    @GetMapping("/me")
    public UserDTO getMyInfo(HttpSession session) {
        // 로그인 후 session에 회원 객체 데이터가 있다고 가정, 통합 시 하단 코드는 삭제 해야 합니다.
        UserDTO userDTO = new UserDTO();
        userDTO.setUsIdx(1);
        session.setAttribute("user", userDTO);
        // 로그인 후 session에 회원 객체 데이터가 있다고 가정, 통합 시 하단 코드는 삭제 해야 합니다.

        // 세션에서 현재 로그인 한 user객체 정보 꺼내기
        userDTO = (UserDTO) session.getAttribute("user");
        return userService.findByUsIdx(userDTO.getUsIdx());
    }

//
//    // 닉네임 수정
//    @PatchMapping("/me/name/{num}")
//    public ResponseEntity<String> updateNickname(@PathVariable int num, @RequestParam String nickname) {
//        userService.updateNickname(num, nickname);
//        return ResponseEntity.ok("Nickname updated successfully");
//    }
//
//    // 비밀번호 변경
//    @PatchMapping("/me/password")
//    public ResponseEntity<String> updatePassword(@RequestParam String currentPassword, @RequestParam String newPassword) {
//        userService.updatePassword(currentPassword, newPassword);
//        return ResponseEntity.ok("Password updated successfully");
//    }
//
//    // 다녀온 여행지 목록 조회
//    @GetMapping("/me/travel-history")
//    public List<String> getTravelHistory() {
//        return userService.getTravelHistory();
//    }
//
//    // 팔로우 목록 조회
//    @GetMapping("/me/follow-list")
//    public List<String> getFollowList() {
//        return userService.getFollowList();
//    }
//}

}
