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
import java.util.Optional;
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

    @Autowired
    private TokenService tokenService;
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
        Optional<User> user = userRepository.findByUsEmail(email);
        if(user.isPresent()){
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
    public ResponseDTO signupStep1(LoginRequest loginRequest) {
        String encodedPassword = passwordEncoder.encode(loginRequest.getUsPassword());
        redisService.set("sign"+loginRequest.getUsEmail(), encodedPassword, 60*60);
        return new ResponseDTO(true, "저장완료");
    }

    //회원가입
    public ResponseDTO signup(UserDTO userDTO) {
        String pw = redisService.get("sign"+userDTO.getUsEmail());
        userDTO.setUsPw(pw);
        userDTO.setUsJoinDate(new Date());
        userDTO.setUsState(1);
        User user = userMapper.toEntity(userDTO);
        userRepository.save(user);
        return new ResponseDTO(true, "회원가입이 완료되었습니다.");
    }
    //로그인
    public UserDTO login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsEmail(loginRequest.getUsEmail());
        if(user.isEmpty()){
            return null;
        }
        else if(user.get().getUsState() != 1){
            return null;
        }
        else if(!passwordEncoder.matches(loginRequest.getUsPassword(),user.get().getUsPw())){
            return null;
        }
        else{
            return userMapper.toDTO(user.get());
        }
    }


    //비밀번호 변경
    public ResponseDTO changePassword(String email, String password){
        Optional<User> user = userRepository.findByUsEmail(email);
        User updatedUser = User.builder()
                .usPw(passwordEncoder.encode(password))
                .usEmail(user.get().getUsEmail())
                .usName(user.get().getUsName())
                .usJoinDate(user.get().getUsJoinDate())
                .usState(user.get().getUsState())
                .build();
        userRepository.save(updatedUser);
        return new ResponseDTO(true, SignupMsgEnum.PASSWORD_CHANGE_SUCCESS.getMessage());

    }
    //임시비밀번호 전송
    public ResponseDTO sendTemporaryPassword(String email){
        String password = CodeGenerator.generateRandomString(10);
        emailService.sendPasswordEmail(email, password);
        Optional<User> user = userRepository.findByUsEmail(email);
        if(user.isPresent()){
            User updatedUser = User.builder()
                    .usIdx(user.get().getUsIdx())
                    .usProfile(user.get().getUsProfile())
                    .usPw(passwordEncoder.encode(password))
                    .usEmail(user.get().getUsEmail())
                    .usName(user.get().getUsName())
                    .usJoinDate(user.get().getUsJoinDate())
                    .usState(user.get().getUsState())
                    .build();
            userRepository.save(updatedUser);
            return new ResponseDTO(true, "임시비밀번호가 전송되었습니다.");
        }
        else{
            return new ResponseDTO(false, "존재하지 않는 이메일입니다.");
        }
        
    }
}
