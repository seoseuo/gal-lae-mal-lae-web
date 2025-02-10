package com.wannago.service;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // usIdx를 통해 User 정보를 가져오는 메서드
    public UserDTO findByUsIdx(int usIdx) {
        Optional<User> userOptional = userRepository.findByUsIdx(usIdx);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }

    // usIdx를 통해 User 이름을 수정하는 메서드
    public void updateUsNameByUsIdx(int usIdx, String usName) {
        userRepository.updateUsNameByUsIdx(usIdx, usName);
    }

    // usName을 통해 User 정보를 가져오는 메서드
    public Boolean chackName(String usName) {
        Optional<User> userOptional = userRepository.findByUsName(usName);
        return userOptional.isPresent();
    }

    // usIdx를 통해 User 비밀번호를 수정하는 메서드
    public void updateUsPwByUsIdx(int usIdx, String usPw) {
        // 비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(usPw);
        userRepository.updateUsPwByUsIdx(usIdx, encodedPw);
    }

    // usIdx를 통해 User 팔로우 목록을 가져오는 메서드
    public List<UserDTO> getFollowList(int usIdx) {
        return userRepository.getFollowList(usIdx);
    }

}
