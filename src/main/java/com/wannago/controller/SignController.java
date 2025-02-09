package com.wannago.controller;

import com.wannago.dto.LoginRequest;
import com.wannago.dto.LoginResponse;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.service.SignService;
import com.wannago.util.jwt.TokenDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sign")

public class SignController {
    @Autowired
    private SignService signService;


    @PostMapping("/email")
    public ResponseEntity<ResponseDTO> sendVerificationEmail(@RequestParam("email") String email) {
        ResponseDTO result = signService.sendVerificationEmail(email);
        return ResponseEntity.ok(result);
    }



    @PostMapping("/email/verify")
    public ResponseEntity<ResponseDTO> verifyEmail(
            @RequestParam("email") String email, 
            @RequestParam("code") String code) {
        ResponseDTO result = signService.checkVerificationCode(email, code);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO userDTO){
        if(!signService.isEmailVerified(userDTO.getUsEmail())){
            //이메일 인증이 필요합니다.
            return ResponseEntity.ok(new ResponseDTO(false,  "이메일 인증이 필요합니다."));
        }else if(signService.isEmailExist(userDTO.getUsEmail())){
            //이미 가입된 이메일입니다.
            return ResponseEntity.ok(new ResponseDTO(false, "이미 가입된 이메일입니다."));
        }else{
            //회원가입 완료
            signService.signup(userDTO);
            return ResponseEntity.ok(new ResponseDTO(true, "회원가입 완료"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        LoginResponse result = signService.login(loginRequest);
        TokenDto token = signService.createToken(result.getUser());
        Cookie accessTokenCookie = new Cookie("accessToken", token.getAccessToken());
        accessTokenCookie.setMaxAge(60*60*24*100);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        Cookie refreshTokenCookie = new Cookie("refreshToken", token.getRefreshToken());
        refreshTokenCookie.setMaxAge(60*60*24*100);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(result.getMessage());









    }
}

