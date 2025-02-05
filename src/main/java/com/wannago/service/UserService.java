package com.wannago.service;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

//    private UserDTO getMyInfo(UserDTO userDTO) {
//
//        Optional<User> userOptional = userRepository.findById(userDTO.getUsIdx());
//        return userOptional.get();
//    }
}
