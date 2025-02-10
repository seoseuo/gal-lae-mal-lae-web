package com.wannago.enums;

public enum VerificationStateEnum {
    VERIFICATION_SUCCESS("success"),
    VERIFICATION_FAILED("failed"),
    CODE_SENT("code_sent"),
    CODE_NOT_FOUND("code_not_found");
    
    private String message;

    VerificationStateEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
