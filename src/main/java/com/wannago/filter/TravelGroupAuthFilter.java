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

        String path = request.getRequestURI();
        String method = request.getMethod();

        // /travelgroups/{grIdx} 패턴 체크
        if (path.matches("/travelgroups/\\d+.*")) {
            int grIdx = Integer.parseInt(path.split("/")[2]);

            if (method.equals("GET")) {
                if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                    return;
                }
            }

            if (method.equals("PATCH")) {
                if (path.contains("/leave")) {
                    if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                        return;
                    }
                    else {
                        filterChain.doFilter(request, response);
                    }
                }
                if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
                    return;
                }
                if (!travelGroupAuthUtil.checkAdminAuth(grIdx)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "모임 회장이 아닙니다.");
                    return;
                }
            }

        }
        filterChain.doFilter(request, response);
    }    
} 