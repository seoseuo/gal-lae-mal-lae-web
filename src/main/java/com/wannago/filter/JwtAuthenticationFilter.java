package com.wannago.filter;

import com.wannago.dto.UserDTO;
import com.wannago.service.SignService;
import com.wannago.util.jwt.JwtProvider;
import com.wannago.util.jwt.TokenDto;
import com.wannago.util.jwt.AccessTokenClaims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wannago.service.TokenService;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private TokenService tokenService;

    

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String path = request.getRequestURI();
            if(path.startsWith("/auth")){
                filterChain.doFilter(request, response);
                return;
            }
        }catch(Exception e){
            log.error("JwtAuthenticationFilter error", e);
        }

        // 쿠키에서 accessToken 찾기
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 토큰이 없으면 필터 통과 후 종료
        if (token == null || token.isEmpty()) {
            System.out.println("토큰이 없습니다.");
            String refreshToken = null;
            
            if (cookies != null) {
                for(Cookie cookie : cookies){
                    if("refreshToken".equals(cookie.getName())){
                        refreshToken = cookie.getValue();
                        break;
                    }
                }
            }
            
            if(refreshToken != null && !refreshToken.isEmpty()){
                TokenDto tokenDto = tokenService.refreshToken(refreshToken);
                if(tokenDto != null){
                    token = tokenDto.getAccessToken();
                    response.addCookie(new Cookie("accessToken", token));
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            filterChain.doFilter(request, response);
            return;
        }

        log.info("JwtAuthenticationFilter {}", token);

        // JWT 토큰 검증
        AccessTokenClaims accessTokenClaims = jwtProvider.getAccessTokenClaims(token, response);
        response.addCookie(new Cookie("accessToken", token));
        log.info("accessTokenClaims {}", accessTokenClaims);

        // UserDTO 객체 생성
        UserDTO userDTO = new UserDTO();
        userDTO.setUsIdx(accessTokenClaims.getUsIdx());
        userDTO.setUsEmail(accessTokenClaims.getUsEmail());
        userDTO.setUsName(accessTokenClaims.getUsName());
        userDTO.setUsProfile(accessTokenClaims.getUsProfile());
        userDTO.setUsState(accessTokenClaims.getUsState());

        // 권한 설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        // 인증 정보 저장
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
