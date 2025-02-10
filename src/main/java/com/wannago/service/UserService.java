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
import com.wannago.repository.FollowRepository;
import lombok.extern.log4j.Log4j2;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserMapper userMapper;

    // usIdx를 통해 User 정보를 가져오는 메서드
    // 내 정보 조회 시 사용
    public UserDTO findByUsIdx(int usIdx) {
        Optional<User> userOptional = userRepository.findByUsIdx(usIdx);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }

    // usIdx를 통해 User 이름을 수정하는 메서드
    // 이름 수정 시 사용
    public void updateUsNameByUsIdx(int usIdx, String usName) {
        userRepository.updateUsNameByUsIdx(usIdx, usName);
    }

    // usName을 통해 User 정보를 가져오는 메서드
    // 중복 체크 시 사용
    public Boolean chackName(String usName) {
        Optional<User> userOptional = userRepository.findByUsName(usName);
        return userOptional.isPresent();
    }

    // usIdx를 통해 User 비밀번호를 수정하는 메서드
    // 비밀번호 변경 시 사용
    public void updateUsPwByUsIdx(int usIdx, String usPw) {
        // 비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPw = encoder.encode(usPw);
        userRepository.updateUsPwByUsIdx(usIdx, encodedPw);
    }

    // usIdx를 통해 User 팔로우 목록을 가져오는 메서드
    // 팔로우 목록 조회 시 사용
    public Map<String, List<UserDTO>> getFollowList(int usIdx) {

        // User가 팔로우 하는 목록
        // User가 팔로우 하는 usIdx 목록        
        // usIdx를 통해 User가 팔로우 하는 목록을 가져오는 메서드
        List<UserDTO> followingUserList = userMapper.toDTOList(userRepository.findByUsIdxInAndUsState(followRepository.getFollowingListByUsIdx(usIdx),1));

        // User를 팔로우 하는 목록
        // User를 팔로우 하는 usIdx 목록            
        // usIdx를 통해 User가 팔로우 받는 목록을 가져오는 메서드                
        List<UserDTO> followerUserList = userMapper.toDTOList(userRepository.findByUsIdxInAndUsState(followRepository.getFollowerListByUsIdx(usIdx),1));

        Map<String, List<UserDTO>> followList = new HashMap<>();
        followList.put("followingList", followingUserList);
        followList.put("followerList", followerUserList);

        return followList;
    }

}
