package com.wannago.service;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Log4j2
@DisplayName("내 정보 열람 테스트")
@SpringBootTest
@Transactional
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;  // Mocking UserRepository

    @Autowired
    private UserMapper userMapper;  // Mocking UserMapper

    @Test
    void testGetMyInfo() {
        Optional<User> userOptional = userRepository.findByUsIdx(1);
        UserDTO userDTO = userOptional.map(userMapper::toDTO).orElse(null);
        log.info("findByUsIdx : {}", userDTO);
    }
}
