package com.wannago.service;

import com.wannago.util.jwt.AccessTokenClaims;
import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.jwt.TokenDto;
import com.wannago.dto.UserDTO;
import com.wannago.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.wannago.repository.UserRepository;

@Service
public class TokenService {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    //토큰발급
    public TokenDto createToken(UserDTO userDTO){
        AccessTokenClaims claims = AccessTokenClaims.builder()
                    .usIdx(userDTO.getUsIdx())
                    .usEmail(userDTO.getUsEmail())
                    .usName(userDTO.getUsName())
                    .usProfile(userDTO.getUsProfile())
                    .usState(userDTO.getUsState())
                    .build();
            return jwtProvider.createToken(claims);
        }
    
        //토큰 재발급
        public TokenDto refreshToken(String refreshToken){
            int usIdx = jwtProvider.getUsIdx(refreshToken);
            Optional<User> user = userRepository.findById(usIdx);
            if(user.isEmpty()){
                return null;
            }
            if(user.get().getUsState() != 1){
                return null;
            }
            AccessTokenClaims claims = AccessTokenClaims.builder()
                    .usIdx(user.get().getUsIdx())
                    .usEmail(user.get().getUsEmail())
                    .usName(user.get().getUsName())
                    .usProfile(user.get().getUsProfile())
                    .usState(user.get().getUsState())
                    .build();
            return jwtProvider.createToken(claims);
        }
}
