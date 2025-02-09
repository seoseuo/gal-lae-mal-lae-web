package com.wannago.filter;

import com.wannago.dto.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.jwt.AccessTokenClaims;

@Log4j2

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 "Authorization" 값 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // 1️⃣ 헤더가 없거나 "Bearer "로 시작하지 않으면 필터를 통과시키고 끝냄
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return; // 필터 종료
        }

        // 2️⃣ "Bearer " 이후의 JWT 토큰 추출
        String token = authorizationHeader.substring(7).trim();

        log.info("JwtAuthenticationFilter {}", token);

        AccessTokenClaims accessTokenClaims = jwtProvider.getAccessTokenClaims(token, response);

        log.info("accessTokenClaims {}", accessTokenClaims);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsIdx(accessTokenClaims.getUsIdx());
        userDTO.setUsEmail(accessTokenClaims.getUsEmail());
        userDTO.setUsName(accessTokenClaims.getUsName());
        userDTO.setUsProfile(accessTokenClaims.getUsProfile());
        userDTO.setUsState(accessTokenClaims.getUsState());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    
        filterChain.doFilter(request, response);
    }
}
