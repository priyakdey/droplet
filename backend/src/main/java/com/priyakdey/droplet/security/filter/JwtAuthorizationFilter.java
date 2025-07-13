package com.priyakdey.droplet.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.priyakdey.droplet.security.jwt.TokenService;
import com.priyakdey.droplet.security.payload.SessionPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * @author Priyak Dey
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private static final Set<String> WHITELIST_PATTERNS = Set.of("/api/auth/**");
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final TokenService<SessionPayload> sessionTokenService;

    public JwtAuthorizationFilter(TokenService<SessionPayload> sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        boolean isWhitelisted = WHITELIST_PATTERNS
                .stream().
                anyMatch(pattern -> PATH_MATCHER.match(pattern, uri));

        if (isWhitelisted) {
            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            sendError(response);
            return;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "token")) {
                token = cookie.getValue();
                break;
            }
        }

        if (token == null || token.isEmpty()) {
            sendError(response);
            return;
        }

        try {
            SessionPayload sessionPayload = sessionTokenService.decode(token);
            String profileId = sessionPayload.sub();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(profileId, null, Set.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            sendError(response);
        }
    }

    private void sendError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String errorJson = """
                {
                  "title": "Unauthorized",
                  "description": "Invalid or expired token."
                }
                """;
        response.getWriter().write(errorJson);
        response.getWriter().flush();
    }

}
