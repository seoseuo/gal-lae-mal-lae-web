package com.wannago.service;

import com.wannago.dto.LoginRequest;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import com.wannago.util.CodeGenerator;
import com.wannago.util.jwt.AccessTokenClaims;

import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.jwt.TokenDto;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtProvider jwtProvider;
    //인증코드 확인


    public ResponseDTO checkVerificationCode(String email, String code) {
        String storedCode = redisService.get(email);
        if(storedCode == null){
            return new ResponseDTO(false, "인증코드가 만료되었거나 존재하지 않습니다.");
        }
        if(storedCode.equals(code)){
            redisService.set(email, "true",60*3);
            return new ResponseDTO(true, "인증코드가 확인되었습니다.");
        }else{
            return new ResponseDTO(false, "인증코드가 확인되지 않았습니다.");
        }
    }

    //인증코드 전송
    public ResponseDTO sendVerificationEmail(String email) {
        String code = CodeGenerator.generateNumericCode(6);
        redisService.set(email, code,60*3);
        emailService.sendVerificationEmail(email, code);
        return new ResponseDTO(true, "인증코드가 발송되었습니다.");

    }
    //이미가입된회원이있는지확인
    public boolean isEmailExist(String email) {
        User user = userRepository.findByUsEmail(email);
        if(user != null){
            return true;
        }
        return false;
    }
    //이메일 인증여부 확인
    public boolean isEmailVerified(String email) {
        String verified = redisService.get(email);
        if(verified.equals("true")){
            return true;
        }
        return false;
    }


    //회원가입
    public ResponseDTO signup(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getUsPw());
        userDTO.setUsPw(encodedPassword);
        userDTO.setUsJoinDate(LocalDateTime.now().toString());
        userDTO.setUsState(1);
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return new ResponseDTO(true, "회원가입이 완료되었습니다.");
    }
    //로그인
    public ResponseDTO login(LoginRequest loginRequest) {
        User user = userRepository.findByUsEmail(loginRequest.getUsEmail());
        if(user == null){
            return new ResponseDTO(false, "존재하지 않는 이메일입니다.");
        }
        if(!passwordEncoder.matches(loginRequest.getUsPw(), user.getUsPw())){
            return new ResponseDTO(false, "비밀번호가 일치하지 않습니다.");
        }
        return new ResponseDTO(true, "로그인이 완료되었습니다.");
    }



    //토큰발급
    public TokenDto createToken(UserDTO userDTO) {
        AccessTokenClaims claims = AccessTokenClaims.builder()
                .usIdx(userDTO.getUsIdx())
                .usEmail(userDTO.getUsEmail())
                .usName(userDTO.getUsName())
                .usProfile(userDTO.getUsProfile())
                .usState(userDTO.getUsState())
                .build();
        TokenDto tokenDto = jwtProvider.createToken(claims);
        String refreshToken = tokenDto.getRefreshToken();
        redisService.set(refreshToken, userDTO.getUsEmail(),60*60*24*100);
        return tokenDto;
    }


}
