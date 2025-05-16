package com.priyakdey.droplet.api.service.v1.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.droplet.api.exception.ServerException;
import com.priyakdey.droplet.api.service.v1.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HS256TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(HS256TokenServiceImpl.class);

    @Override
    public String generate(Integer id, String name) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "hs256");
        headers.put("typ", "jwt");

        Instant iat = Instant.now(Clock.systemUTC());
        Instant eat = iat.plus(1, ChronoUnit.HOURS);

        try {
            return JWT.create()
                    .withHeader(headers)
                    .withSubject(id.toString())
                    .withClaim("name", name)
                    .withIssuer("droplet")
                    .withIssuedAt(iat)
                    .withExpiresAt(eat)
                    .sign(Algorithm.HMAC256("secret"));
        } catch (IllegalArgumentException | JWTCreationException e) {
            logger.error("Failed to generate the token:", e);
            throw new ServerException();
        }
    }

    @Override
    public DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256("secret"))
                .withIssuer("droplet")
                .acceptLeeway(10)
                .build();
        return verifier.verify(token);
    }

    @Override
    public Integer getSubject(DecodedJWT jwt) {
        return Integer.parseInt(jwt.getSubject());
    }
}
