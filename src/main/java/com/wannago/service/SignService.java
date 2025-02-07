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
    //회원가입
    public String signup(UserDTO userDTO) {
        User user = userRepository.findByUsEmail(userDTO.getUsEmail());
        if(user != null){
            return "이미 가입된 이메일입니다."; 
        }
        user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
