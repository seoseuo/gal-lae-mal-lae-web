package com.wannago.service;

import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import com.wannago.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisService redisService;
    
    //인증코드 확인
    public boolean checkVerificationCode(String email, String code) {
        String storedCode = redisService.getCode(email);
        return storedCode.equals(code);
    }
    //인증코드 전송
    public String sendVerificationEmail(String email) {
        String code = CodeGenerator.generateNumericCode(6);
        redisService.setCode(email, code);
        return emailService.sendVerificationEmail(email, code);
    }
}
