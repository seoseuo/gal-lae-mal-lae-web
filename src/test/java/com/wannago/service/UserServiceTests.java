package com.wannago.service;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Log4j2
@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;  

    @Autowired
    private UserMapper userMapper;  

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("내 정보 열람 테스트")
    void testGetMyInfo() {
        Optional<User> userOptional = userRepository.findByUsIdx(1);
        UserDTO userDTO = userOptional.map(userMapper::toDTO).orElse(null);        
        log.info("내 정보 : {}", userDTO);        
    }

    @Test
    @DisplayName("이름 수정 테스트")
    void testUpdateUsNameByUsIdx() {
        userService.updateUsNameByUsIdx(1, "서승팔");                
    }

}
