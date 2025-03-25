package com.wannago.service;

import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.dto.newPassWordDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Log4j2
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserMapper userMapper;

    @Value("${file.image.upload-path}")
    private String imageUploadPath;

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
    public ResponseDTO updateUsPwByUsIdx(int usIdx, newPassWordDTO newPassWordDTO) {
        // 비밀번호 암호화
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodeUsPw = encoder.encode(newPassWordDTO.getUsPw());
        String encodedPw = encoder.encode(newPassWordDTO.getNewUsPw());
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<User> user = userRepository.findByUsIdx(usIdx);
        
        if(user.get().getUsPw().equals(encodeUsPw)){
            responseDTO.setSuccess(false);
            responseDTO.setMessage("기존 비밀번호가 일치하지 않습니다.");
            return responseDTO;
        }
        
        userRepository.updateUsPwByUsIdx(usIdx, encodedPw);
        responseDTO.setSuccess(true);
        responseDTO.setMessage("비밀번호 변경에 성공하였습니다.");
        return responseDTO;
    }

    // usIdx를 통해 User 팔로우 목록을 가져오는 메서드
    // 팔로우 목록 조회 시 사용
    public Map<String, List<UserDTO>> getFollowList(int usIdx) {

        // User가 팔로우 하는 목록
        // User가 팔로우 하는 usIdx 목록
        // usIdx를 통해 User가 팔로우 하는 목록을 가져오는 메서드
        List<UserDTO> followingUserList = userMapper
                .toDTOList(userRepository.findByUsIdxInAndUsState(followRepository.getFollowingListByUsIdx(usIdx), 1));

        // User를 팔로우 하는 목록
        // User를 팔로우 하는 usIdx 목록
        // usIdx를 통해 User가 팔로우 받는 목록을 가져오는 메서드
        List<UserDTO> followerUserList = userMapper
                .toDTOList(userRepository.findByUsIdxInAndUsState(followRepository.getFollowerListByUsIdx(usIdx), 1));

        Map<String, List<UserDTO>> followList = new HashMap<>();
        followList.put("followingList", followingUserList);
        followList.put("followerList", followerUserList);

        return followList;
    }

    // usIdx를 통해 User 프로필 이미지를 수정하는 메서드
    // 프로필 이미지 수정 시 사용
    public void updateUsProfileByUsIdx(int usIdx, MultipartFile file) {
        // 1. 해당 유저 조회 (없으면 예외 발생)
        User user = userRepository.findByUsIdx(usIdx)
                .orElseThrow(() -> new RuntimeException("User not found with usIdx: " + usIdx));

        String fileName = user.getUsEmail() + "_profile";
        
        // 2. 기존 프로필이 기본 이미지가 아니면 삭제
        if (!"profile.png".equals(user.getUsProfile())) {
            File profileDir = new File(imageUploadPath);
            File[] matchingFiles = profileDir.listFiles((dir, name) -> name.startsWith(fileName + "."));
            if (matchingFiles != null) {
                for (File matchingFile : matchingFiles) {
                    matchingFile.delete();
                }
            }
        }

        // 3. 새 파일 저장
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new RuntimeException("Invalid file name");
        }

        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uploadFileName = fileName + fileExtension;
        File newFile = new File(imageUploadPath + uploadFileName);

        try {
            Files.copy(file.getInputStream(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        // 4. DB 업데이트 (파일명 변경)
        userRepository.updateUsProfileByUsIdx(usIdx, uploadFileName);
    }

    // usEmail을 통해 User 정보를 가져오는 메서드
    // 검색 시 사용
    public UserDTO findByUsEmail(String usEmail) {
        Optional<User> userOptional = userRepository.findByUsEmail(usEmail);
        return userOptional.map(userMapper::toDTO).orElse(null);
    }
}
