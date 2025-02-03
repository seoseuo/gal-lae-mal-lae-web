package com.wannago.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int usIdx;
    private String usEmail;
    private String usPw;
    private String usJoinDate;
    private String usLeaveDate;
    private int usState;
}
