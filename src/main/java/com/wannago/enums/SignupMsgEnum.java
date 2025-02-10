package com.wannago.enums;

public enum SignupMsgEnum {
    EMAIL_VERIFICATION_REQUIRED("이메일 인증이 필요합니다."),
    EMAIL_ALREADY_EXISTS("이미 가입된 이메일입니다."),
    SIGNUP_SUCCESS("회원가입 완료"),
    PASSWORD_CHANGE_SUCCESS("비밀번호 변경 완료");


    private String message;

    SignupMsgEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
