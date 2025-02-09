package com.wannago.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
@Log4j2
@Component
public class JwtProvider {

    private final int ACCESS_TOKEN_EXPIRES_IN = 60 * 60;
    private final int REFRESH_TOKEN_EXPIRES_IN = 60 * 60 * 24 * 100;

    @Value("${custom.jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${custom.jwt.subject}")
    private String SUBJECT;

    private SecretKey secretKey;

public SecretKey getSecretKey() {
    if (secretKey == null) {
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 
    }
    return secretKey;
}


    private String genToken(Map<String, Object> map, int seconds) {
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(SUBJECT)
                .setExpiration(accessTokenExpiresIn);

        if (map != null) {
            Set<String> keys = map.keySet();
            Iterator<String> it = keys.iterator();

            while (it.hasNext()) {
                String key = it.next();
                Object value = map.get(key);
                jwtBuilder.claim(key, value);
            }
        }

        return jwtBuilder.signWith(SignatureAlgorithm.HS256, getSecretKey()).compact();
    }

    public TokenDto createToken(AccessTokenClaims claims) {
        String accessToken = genToken(claims.toMap(), ACCESS_TOKEN_EXPIRES_IN);
        String refreshToken = genToken(null, REFRESH_TOKEN_EXPIRES_IN);

        return new TokenDto(accessToken, refreshToken);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 비밀키 설정
                    .build()  // 파서 빌드
                    .parseClaimsJws(token)  // JWT 파싱
                    .getBody();  // JWT의 바디 반환
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AccessTokenClaims getAccessTokenClaims(String token, HttpServletResponse response) throws IOException {         
        try {
            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())  // 비밀 키 설정
                    .build()  // 파서 빌드
                    .parseClaimsJws(token)  // 토큰 파싱
                    .getBody();  // JWT의 바디 반환

            Integer idx = payload.get("USIDX", Integer.class);
            String email = payload.get("USEMAIL", String.class);
            String name = payload.get("USNAME", String.class);
            String img = payload.get("USPROFILE", String.class);
            int state = payload.get("USSTATE",  Integer.class);

            log.info("idx : {}",idx);
            log.info("email : {}",email);
            log.info("name : {}",name);
            log.info("img : {}",img);
            log.info("state : {}",state);


            return AccessTokenClaims.builder()
                    .usIdx(idx)
                    .usEmail(email)
                    .usName(name)
                    .usProfile(img)
                    .usState(state)
                    .build();

        } catch (ExpiredJwtException e) {
            log.info("Token has expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"Token has expired.\"}";
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            return null;

        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token format: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"Invalid JWT token format.\"}";
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            return null;

        } catch (SignatureException e) {
            log.info("Invalid JWT signature: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"Invalid JWT signature.\"}";
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            return null;
            
        } catch (JwtException e) {
            log.info("JWT-related exception occurred: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            String jsonResponse = "{\"status\":\"error\", \"message\":\"A JWT-related exception occurred.\"}";
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            return null;
        }
    }
}
