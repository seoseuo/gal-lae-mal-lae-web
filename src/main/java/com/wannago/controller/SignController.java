package com.wannago.controller;

import com.wannago.service.SignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sign")
public class SignController {
    @Autowired
    private SignService signService;


    @PostMapping("/email")
    public ResponseEntity<String> sendVerificationEmail(@RequestParam("email") String email) {
        String result = signService.sendVerificationEmail(email);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/email/verify")
    public ResponseEntity<String> verifyEmail(
            @RequestParam("email") String email, 
            @RequestParam("code") String code) {
        boolean result = signService.checkVerificationCode(email, code);
        String message = "";
        if(result){
            message = "인증코드가 확인되었습니다.";
        }else{
            message = "인증코드가 확인되지 않았습니다.";
        }
        return ResponseEntity.ok(message);
    }
}
