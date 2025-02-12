package com.wannago.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.wannago.util.TravelGroupAuthUtil;

@Log4j2
@Component
public class TravelGroupAuthFilter extends OncePerRequestFilter {
    
    @Autowired
    private TravelGroupAuthUtil travelGroupAuthUtil;
    

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();
            String method = request.getMethod();

            if (path.matches("/travelgroups/\\d+.*")) {
                int grIdx = Integer.parseInt(path.split("/")[2]);

                if (method.equals("GET")) {
                    if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                        sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                        return;
                    }
                }

                if (method.equals("PATCH")) {
                    if (path.contains("/leave")) {
                        if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                            return;
                        }
                    }
                    if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                        sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                        return;
                    }
                    if (!travelGroupAuthUtil.checkAdminAuth(grIdx)) {
                        sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회장이 아닙니다.");
                        return;
                    }
                }
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("Filter error: ", e);
            if (!response.isCommitted()) {
                sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
            }
        }
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.resetBuffer();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.getWriter().write(message);
    }
} 