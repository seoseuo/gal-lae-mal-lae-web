package com.wannago.controller;

import com.wannago.dto.LoginRequest;
import com.wannago.dto.LoginResponse;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.enums.LoginStatusEnum;
import com.wannago.enums.SignupMsgEnum;
import com.wannago.enums.VerificationStateEnum;
import com.wannago.service.SignService;
import com.wannago.util.jwt.TokenDto;
import com.wannago.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")    
public class SignController {
    @Autowired
    private SignService signService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/verification/email")
    public ResponseEntity<ResponseDTO> sendVerificationEmail(@RequestParam("email") String email) {
        ResponseDTO result = signService.sendVerificationEmail(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verification/confirm")
    public ResponseEntity<ResponseDTO> verifyEmail(
            @RequestParam("email") String email, 
            @RequestParam("code") String code) {
        ResponseDTO result = signService.checkVerificationCode(email, code);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/signup/step1")
    public ResponseEntity<ResponseDTO> signupStep1(@RequestBody LoginRequest loginRequest){
        if(!signService.isEmailVerified(loginRequest.getUsEmail())){
            return ResponseEntity.ok(new ResponseDTO(false, SignupMsgEnum.EMAIL_VERIFICATION_REQUIRED.getMessage()));
        }else if(signService.isEmailExist(loginRequest.getUsEmail())){
            return ResponseEntity.ok(new ResponseDTO(false, SignupMsgEnum.EMAIL_ALREADY_EXISTS.getMessage()));
        }
        return ResponseEntity.ok(signService.signupStep1(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody UserDTO userDTO){
        if(signService.isEmailExist(userDTO.getUsEmail())){
            return ResponseEntity.ok(new ResponseDTO(false, SignupMsgEnum.EMAIL_ALREADY_EXISTS.getMessage()));
        }else{
            signService.signup(userDTO);
            return ResponseEntity.ok(new ResponseDTO(true, SignupMsgEnum.SIGNUP_SUCCESS.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        UserDTO result = signService.login(loginRequest);
        if(result == null){
            return ResponseEntity.ok(new ResponseDTO(false, "로그인실패"));
        }
        TokenDto token = tokenService.createToken(result);
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
        return ResponseEntity.ok(new ResponseDTO(true, "로그인성공"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout(HttpServletResponse response){
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.ok(new ResponseDTO(true, LoginStatusEnum.LOGOUT_SUCCESS.getMessage()));
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseDTO> changePassword(@RequestParam("email") String email, @RequestParam("password") String password){
        if(!signService.isEmailVerified(email)){
            return ResponseEntity.ok(new ResponseDTO(false, VerificationStateEnum.VERIFICATION_FAILED.getMessage()));
        }
        ResponseDTO result = signService.changePassword(email, password);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/temporary/password")
    public ResponseEntity<ResponseDTO> sendTemporaryPassword(@RequestParam("email") String email){
        ResponseDTO result = signService.sendTemporaryPassword(email);
        return ResponseEntity.ok(result);
    }
}

