package com.wannago.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PostDTO {
    private int poIdx;
    private String poTitle;
    private String poContent;
    private int usIdx;
    private Date poCreatedAt;    
    private Date poDeletedAt;
    private int poState;
    private String ldIdx;
}
