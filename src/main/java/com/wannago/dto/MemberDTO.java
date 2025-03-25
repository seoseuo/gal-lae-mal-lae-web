package com.wannago.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private int grIdx;
    private int usIdx;
    private String meRole;
    private UserDTO meUser;
}
