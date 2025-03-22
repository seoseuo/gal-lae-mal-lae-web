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
import io.jsonwebtoken.JwtException;

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            if (path.startsWith("/auth") || path.startsWith("/ws")) {
                filterChain.doFilter(request, response);
                return;
            }

            String accessToken = jwtProvider.getAccessTokenFromCookie(request);
            String refreshToken = jwtProvider.getRefreshTokenFromCookie(request);

            if (accessToken == null && refreshToken == null) {
                log.info("토큰이 없습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            try {
                AccessTokenClaims accessTokenClaims = jwtProvider.getAccessTokenClaims(accessToken);
                if (accessTokenClaims != null) {
                    log.info("유효한 액세스 토큰이 있습니다.");
                    setAuthentication(accessTokenClaims);
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (JwtException e) {
                log.info("액세스 토큰 검증 실패: {}", e.getMessage());
            }

            if (refreshToken != null) {
                try {
                    TokenDto tokenDto = tokenService.refreshToken(refreshToken);
                    if (tokenDto != null) {
                        log.info("리프레시 토큰이 유효합니다. 새로운 액세스 토큰을 발급합니다.");
                        Cookie newCookie = new Cookie("accessToken", tokenDto.getAccessToken());
                        newCookie.setPath("/");
                        newCookie.setHttpOnly(true);
                        response.addCookie(newCookie);
                        
                        AccessTokenClaims claims = jwtProvider.getAccessTokenClaims(tokenDto.getAccessToken());
                        if (claims != null) {
                            setAuthentication(claims);
                        }
                        
                        filterChain.doFilter(request, response);
                        return;
                    }
                } catch (Exception e) {
                    log.info("리프레시 토큰 검증 실패: {}", e.getMessage());
                }
            }

            log.info("모든 토큰이 만료되었습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"인증이 필요합니다.\"}");

        } catch (Exception e) {
            log.error("인증 처리 중 오류 발생: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"message\":\"서버 오류가 발생했습니다.\"}");
        }
    }

    private void setAuthentication(AccessTokenClaims claims) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsIdx(claims.getUsIdx());
        userDTO.setUsEmail(claims.getUsEmail());
        userDTO.setUsName(claims.getUsName());
        userDTO.setUsProfile(claims.getUsProfile());
        userDTO.setUsState(claims.getUsState());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER_ROLE"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
