package com.wannago.util.jwt;

import jakarta.servlet.http.Cookie;
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
import jakarta.servlet.http.HttpServletRequest;
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

    private String genRefreshToken(int usIdx,int expiresIn){
        Map<String, Object> map = new HashMap<>();
        map.put("USIDX", usIdx);
        return genToken(map, expiresIn);
    }

    public TokenDto createToken(AccessTokenClaims claims) {
        String accessToken = genToken(claims.toMap(), ACCESS_TOKEN_EXPIRES_IN);
        String refreshToken = genRefreshToken(claims.getUsIdx(), REFRESH_TOKEN_EXPIRES_IN);

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

    public AccessTokenClaims getAccessTokenClaims(String token) throws JwtException {
        try {
            Claims payload = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Integer idx = payload.get("USIDX", Integer.class);
            String email = payload.get("USEMAIL", String.class);
            String name = payload.get("USNAME", String.class);
            String img = payload.get("USPROFILE", String.class);
            int state = payload.get("USSTATE", Integer.class);

            log.info("idx : {}", idx);
            log.info("email : {}", email);
            log.info("name : {}", name);
            log.info("img : {}", img);
            log.info("state : {}", state);

            return AccessTokenClaims.builder()
                    .usIdx(idx)
                    .usEmail(email)
                    .usName(name)
                    .usProfile(img)
                    .usState(state)
                    .build();

        } catch (ExpiredJwtException e) {
            log.info("Token has expired: {}", e.getMessage());
            throw new JwtException("토큰이 만료되었습니다.");

        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token format: {}", e.getMessage());
            throw new JwtException("잘못된 토큰 형식입니다.");

        } catch (SignatureException e) {
            log.info("Invalid JWT signature: {}", e.getMessage());
            throw new JwtException("유효하지 않은 토큰 서명입니다.");

        } catch (JwtException e) {
            log.info("JWT-related exception occurred: {}", e.getMessage());
            throw new JwtException("JWT 관련 오류가 발생했습니다.");

        } catch(Exception e) {
            log.info("JWT-related exception occurred: {}", e.getMessage());
            throw new JwtException("JWT 처리 중 오류가 발생했습니다.");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"message\":\"" + message + "\"}");
        response.getWriter().flush();
    }
    
    public Integer getUsIdxFromToken(String token){
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return payload.get("USIDX", Integer.class);
    }

    public AccessTokenClaims getAccessTokenClaimsFromToken(String token){
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return AccessTokenClaims.builder()
                .usIdx(payload.get("USIDX", Integer.class))
                .usEmail(payload.get("USEMAIL", String.class))
                .usName(payload.get("USNAME", String.class))
                .usProfile(payload.get("USPROFILE", String.class))
                .usState(payload.get("USSTATE", Integer.class))
                .build();
    }

    public int getUsIdx(String token){
        Claims payload = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return payload.get("USIDX", Integer.class);
    }

    public String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String createAccessToken(AccessTokenClaims claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_EXPIRES_IN * 1000);

        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(expiration)
                .claim("USIDX", claims.getUsIdx())
                .claim("USEMAIL", claims.getUsEmail())
                .claim("USNAME", claims.getUsName())
                .claim("USPROFILE", claims.getUsProfile())
                .claim("USSTATE", claims.getUsState())
                .signWith(getSecretKey())
                .compact();
    }
}