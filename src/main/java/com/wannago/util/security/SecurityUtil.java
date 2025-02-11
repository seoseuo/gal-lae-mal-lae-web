package com.wannago.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;
import com.wannago.dto.UserDTO;

@Log4j2
@Component
public class SecurityUtil {
    // authentication 에서 UserDTO 추출
    public UserDTO getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User from authentication: {}", authentication);
        return (UserDTO) authentication.getPrincipal();
    }
}
