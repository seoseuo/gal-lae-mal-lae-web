package com.wannago.dto;

public class ChatDTO {
    private Integer sender;
    private Integer receiver;
    private String message;
    
    // Getter, Setter
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
} 