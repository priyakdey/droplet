package com.priyakdey.droplet.service.impl;

import com.priyakdey.droplet.security.SessionPayload;
import com.priyakdey.droplet.security.service.TokenService;
import com.priyakdey.droplet.service.CookieService;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class CookieServiceImpl implements CookieService {
    private final TokenService<SessionPayload> sessionTokenService;

    public CookieServiceImpl(TokenService<SessionPayload> sessionTokenService) {
        this.sessionTokenService = sessionTokenService;
    }

    @Override
    public ResponseCookie getCookie(SessionPayload payload) {
        String token = sessionTokenService.generate(payload);

        return ResponseCookie.from("token", token)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(payload.expirationInSec())
                .build();
    }
}
