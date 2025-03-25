package com.wannago.util.jwt;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessTokenClaims {
    
    private int usIdx;
    private String usEmail;
    private String usName;
    private String usProfile;
    private int usState;

    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("USIDX", usIdx);
        map.put("USEMAIL", usEmail);
        map.put("USNAME", usName);
        map.put("USPROFILE", usProfile);
        map.put("USSTATE", usState);
        // key 값 수정 2025-02-10 서승권

        return map;

    }
}
