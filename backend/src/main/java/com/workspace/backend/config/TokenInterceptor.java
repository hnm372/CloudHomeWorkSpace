package com.workspace.backend.config;

import com.workspace.backend.commom.RE;
import com.workspace.backend.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private final TokenUtils tokenUtils;

    public TokenInterceptor(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (token != null && token.regionMatches(true, 0, "Bearer ", 0, 7)) {
            token = token.substring(7).trim();
        }
        String authenticatedUuid = tokenUtils.verifyAndGetUuid(token);
        if (authenticatedUuid != null) {
            request.setAttribute("authenticatedUserUuid", authenticatedUuid);
            return true;
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("{\"code\":" + RE.UNAUTHORIZED.getCode() + ",\"message\":\"Unauthorized\"}");
        return false;
    }
}
