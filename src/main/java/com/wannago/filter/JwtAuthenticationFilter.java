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

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 임의로 만든 시크릿 키 입니다.
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(Base64.getEncoder().encodeToString("wannago-secret-key-extend-this-key-to-be-long-enough".getBytes(StandardCharsets.UTF_8)))
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        // 요청 헤더에서 "Authorization" 값 가져오기
        String authorizationHeader = request.getHeader("Authorization");

        // 1️⃣ 헤더가 없거나 "Bearer "로 시작하지 않으면 필터를 통과시키고 끝냄
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return; // 필터 종료
        }

        // 2️⃣ "Bearer " 이후의 JWT 토큰 추출
        String token = authorizationHeader.substring(7).trim();


        // log.info("JwtAuthenticationFilter {}", token);

        try {
            // 3️⃣ JWT 검증 및 사용자 정보 추출
            Claims claims = Jwts.parser()
                    .verifyWith((SecretKey) SECRET_KEY) // 새로운 메서드 사용
                    .build().parseSignedClaims(token)
                    .getPayload();


            UserDTO userDTO = new UserDTO();
            userDTO.setUsIdx(claims.get("usIdx", Integer.class)); // 회원 고유 ID (usIdx)
            userDTO.setUsName(claims.get("usName", String.class));   // 사용자 이름
            userDTO.setUsEmail(claims.get("usEmail", String.class)); // 이메일
            userDTO.setUsState(claims.get("usState", Integer.class)); // 사용자 상태
            String role = claims.get("role", String.class);

            //userDTO.setUsJoinDate(String.valueOf(claims.get("iat"))); // 가입일 (Epoch 형식 -> String으로 변환)
            //userDTO.setUsLeaveDate(claims.get("exp", String.class)); //

            // 4️⃣ 인증 객체 생성 및 SecurityContext에 저장
            List<GrantedAuthority> authorities = new ArrayList<>();

            if (role.equals("USER_ROLE")) {
                authorities.add(new SimpleGrantedAuthority("USER_ROLE"));  // role을 권한으로 변환
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO, null, authorities);
            log.info("Authentication : {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("USER : {}", userDTO);
            log.info("ROLE : {}", role);

        } catch (ExpiredJwtException e) {
            log.info("토큰이 만료되었습니다: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 만료된 토큰 처리
            response.setContentType("application/json");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"토큰이 만료되었습니다.\"}";
            response.getWriter().write(jsonResponse);
            return;
        } catch (MalformedJwtException e) {
            log.info("잘못된 JWT 토큰 형식입니다: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 잘못된 JWT 형식
            response.setContentType("application/json");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"잘못된 JWT 토큰 형식입니다.\"}";
            response.getWriter().write(jsonResponse);
            return;
        } catch (SignatureException e) {
            log.info("유효하지 않은 JWT 서명입니다: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 서명 오류 처리
            response.setContentType("application/json");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"유효하지 않은 JWT 서명입니다.\"}";
            response.getWriter().write(jsonResponse);
            return;
        } catch (JwtException e) {
            log.info("JWT 관련 예외가 발생했습니다: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // JWT 관련 다른 예외 처리
            response.setContentType("application/json");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"JWT 관련 예외가 발생했습니다.\"}";
            response.getWriter().write(jsonResponse);
            return;
        }

        // 6️⃣ 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}