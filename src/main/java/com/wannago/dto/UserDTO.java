package com.wannago.dto;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class UserDTO {
    private int usIdx;
    private String usEmail;
    private String usName;
    private String usPw;
    private String usJoinDate;
    private String usLeaveDate;
    private int usState;
}
