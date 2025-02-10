package com.wannago.enums;

public enum LoginStatusEnum {
    LOGIN_SUCCESS("success"),
    WRONG_EMAIL("email_not_found"),
    WRONG_PASSWORD("password_not_match"),
    WRONG_STATE("state_not_active"),
    LOGOUT_SUCCESS("logout_success");
    private String message;


    LoginStatusEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
