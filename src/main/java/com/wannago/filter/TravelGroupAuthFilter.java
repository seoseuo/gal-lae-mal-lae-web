package com.wannago.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.wannago.util.TravelGroupAuthUtil;

@Log4j2
@Component
@RequiredArgsConstructor
public class TravelGroupAuthFilter extends OncePerRequestFilter {

    private final TravelGroupAuthUtil travelGroupAuthUtil;
    private static final String GROUP_PATH_PATTERN = "/travelgroups/\\d+.*";
    private static final String TRAVEL_PATH_PATTERN = "^/travelgroups/\\d+/travel.*$";
    private static final String LEAVE_PATH_PATTERN = "^/travelgroups/\\d+/leave$";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        if (!path.matches(GROUP_PATH_PATTERN)) {
            filterChain.doFilter(request, response);
            return;
        }

        int grIdx = extractGroupIndex(path);

        if (!checkMemberAuth(grIdx, response)) {
            return;
        }

        if (requiresAdminAuth(path, method) && !checkAdminAuth(grIdx, response)) {
            return;
        }

        filterChain.doFilter(request, response);
    }

    private int extractGroupIndex(String path) {
        return Integer.parseInt(path.split("/")[2]);
    }

    private boolean checkMemberAuth(int grIdx, HttpServletResponse response) throws IOException {
        if (!travelGroupAuthUtil.checkMemberAuth(grIdx)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회원이 아닙니다.");
            return false;
        }
        return true;
    }

    private boolean checkAdminAuth(int grIdx, HttpServletResponse response) throws IOException {
        if (!travelGroupAuthUtil.checkAdminAuth(grIdx)) {
            sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "모임 회장이 아닙니다.");
            return false;
        }
        return true;
    }

    private boolean requiresAdminAuth(String path, String method) {
        if (path.matches(TRAVEL_PATH_PATTERN)) {
            return true;
        }

        if (path.matches(LEAVE_PATH_PATTERN)) {
            return false;
        }

        return "PATCH".equals(method) || "DELETE".equals(method);
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.resetBuffer();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.getWriter().write(message);
    }
}