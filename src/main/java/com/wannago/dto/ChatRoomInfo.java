package com.wannago.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomInfo {
    private Integer crIdx;
    private UserDTO myUser;
    private UserDTO otherUser;
}
