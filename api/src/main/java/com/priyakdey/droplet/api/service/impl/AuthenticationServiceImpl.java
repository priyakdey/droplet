package com.priyakdey.droplet.api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.priyakdey.droplet.api.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Priyak Dey
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    // TODO: read from env variables
    private static final String SECRET = "secret";
    private static final String ISSUER = "droplet-dev";
    private static final long timeDelta = 86400;

    @Override
    public String generateToken(Long userId) {
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");

        Instant iat = Instant.now(Clock.systemUTC());
        Instant eat = iat.plus(timeDelta, ChronoUnit.SECONDS);

        return JWT.create()
                .withHeader(headerClaims)
                .withSubject(userId.toString())
                .withIssuer(ISSUER)
                .withIssuedAt(iat)
                .withExpiresAt(eat)
                .sign(Algorithm.HMAC256(SECRET));

    }
}
