package com.priyakdey.droplet.security.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.droplet.security.jwt.TokenVerifier;

import java.util.function.Function;

/**
 * @author Priyak Dey
 */
public class Hmac256Verifier<T> implements TokenVerifier<T> {

    private final Algorithm algorithm;
    private final Function<DecodedJWT, T> deserializer;
    private final String issuer;
    private final int leewayInSec;

    public Hmac256Verifier(byte[] secret, Function<DecodedJWT, T> deserializer, String issuer,
                           int leewayInSec) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.deserializer = deserializer;
        this.issuer = issuer;
        this.leewayInSec = leewayInSec;
    }

    @Override
    public T verify(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .acceptLeeway(leewayInSec)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return deserializer.apply(jwt);
    }
}
