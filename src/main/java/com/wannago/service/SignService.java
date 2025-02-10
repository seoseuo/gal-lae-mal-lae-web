package com.wannago.service;

import com.wannago.dto.LoginRequest;
import com.wannago.dto.LoginResponse;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import com.wannago.enums.LoginStatusEnum;
import com.wannago.enums.SignupMsgEnum;
import com.wannago.enums.VerificationStateEnum;
import com.wannago.mapper.UserMapper;
import com.wannago.repository.UserRepository;
import com.wannago.util.CodeGenerator;
import com.wannago.util.jwt.AccessTokenClaims;

import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.jwt.TokenDto;

import java.time.LocalDateTime;
import java.util.Date;

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
            return new ResponseDTO(false, VerificationStateEnum.CODE_NOT_FOUND.getMessage());
        }


        if(storedCode.equals(code)){
            redisService.set(email, "true",60*3);
            return new ResponseDTO(true, VerificationStateEnum.VERIFICATION_SUCCESS.getMessage());
        }else{
            return new ResponseDTO(false, VerificationStateEnum.VERIFICATION_FAILED.getMessage());
        }

    }

    //인증코드 전송
    public ResponseDTO sendVerificationEmail(String email) {
        String code = CodeGenerator.generateNumericCode(6);
        redisService.set(email, code,60*3);
        emailService.sendVerificationEmail(email, code);
        return new ResponseDTO(true, VerificationStateEnum.CODE_SENT.getMessage());
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
        String trueValue = "true";
        return trueValue.equals(verified);

    }


    //회원가입
    public ResponseDTO signup(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getUsPw());
        userDTO.setUsPw(encodedPassword);
        userDTO.setUsJoinDate(new Date());
        userDTO.setUsState(1);
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);

        return new ResponseDTO(true, "회원가입이 완료되었습니다.");
    }
    //로그인
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsEmail(loginRequest.getUsEmail());
        if(user == null){
            return new LoginResponse(null,LoginStatusEnum.WRONG_EMAIL.getMessage());
        }
        else if(user.getUsState() != 1){
            return new LoginResponse(null,LoginStatusEnum.WRONG_STATE.getMessage());
        }
        else if(!passwordEncoder.matches(loginRequest.getUsPw(), user.getUsPw())){
            return new LoginResponse(null,LoginStatusEnum.WRONG_PASSWORD.getMessage());
        }
        else{
            return new LoginResponse(userMapper.toDTO(user),LoginStatusEnum.LOGIN_SUCCESS.getMessage());
        }
    }

    //토큰발급
    public TokenDto createToken(UserDTO userDTO){
        AccessTokenClaims claims = AccessTokenClaims.builder()
                .usIdx(userDTO.getUsIdx())
                .usEmail(userDTO.getUsEmail())
                .usName(userDTO.getUsName())
                .usProfile(userDTO.getUsProfile())
                .usState(userDTO.getUsState())
                .build();
        return jwtProvider.createToken(claims);
    }
    //비밀번호 변경
    public ResponseDTO changePassword(String email, String password){
        User user = userRepository.findByUsEmail(email);
        User updatedUser = User.builder()
                .usPw(passwordEncoder.encode(password))
                .usEmail(user.getUsEmail())
                .usName(user.getUsName())
                .usJoinDate(user.getUsJoinDate())
                .usState(user.getUsState())
                .build();
        userRepository.save(updatedUser);
        return new ResponseDTO(true, SignupMsgEnum.PASSWORD_CHANGE_SUCCESS.getMessage());

    }
}
