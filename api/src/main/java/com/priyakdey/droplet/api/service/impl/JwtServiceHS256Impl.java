package com.priyakdey.droplet.api.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.priyakdey.droplet.api.service.JwtService;
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
public class JwtServiceHS256Impl implements JwtService {
    private final static Map<String, Object> headerClaims;

    // TODO: read from env variables
    // TODO: These would be common across algorithms, maybe bind values in a struct, and then inject in service
    private static final String SECRET = "secret";
    private static final String ISSUER = "droplet-dev";
    private static final long timeDelta = 5 * 60;

    static {
        headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
    }

    @Override
    public String generateToken(Long userId) {
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
