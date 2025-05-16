package com.priyakdey.droplet.api.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.droplet.api.service.v1.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

/**
 * @author Priyak Dey
 */
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final Set<String> WHITELIST = Set.of("/v1/login", "/v1/signup");

    private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (WHITELIST.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String[] parts = token.split("\\s+");
        token = parts[1];
        if (parts.length != 2) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            DecodedJWT decodedJWT = tokenService.verify(token);
            Integer accountId = tokenService.getSubject(decodedJWT);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(accountId, null, Set.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("Could not parse and verify the token: ", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
