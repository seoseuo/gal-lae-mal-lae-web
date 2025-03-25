package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int usIdx;
    private String usName;
    private String usProfile;
    private String usEmail;
    private String usPw;
    private Date usJoinDate;
    private Date usLeaveDate;
    private int usState;
}
