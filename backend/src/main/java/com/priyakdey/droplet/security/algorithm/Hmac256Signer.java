package com.priyakdey.droplet.security.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.priyakdey.droplet.security.jwt.TokenSigner;

import java.time.Instant;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Priyak Dey
 */
public class Hmac256Signer<T> implements TokenSigner<T> {

    private final Algorithm algorithm;
    private final Function<T, Map<String, ?>> serializer;
    private final Map<String, Object> headerClaims;

    private final String issuer;
    private final int expirationInSec;

    public Hmac256Signer(byte[] secret, Function<T, Map<String, ?>> serializer,
                         String issuer, int expirationInSec) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.serializer = serializer;
        this.issuer = issuer;
        this.expirationInSec = expirationInSec;
        this.headerClaims = Map.of("alg", algorithm.getName(), "typ", "jwt");
    }

    @Override
    public String sign(T payload) {
        Instant iat = Instant.now();
        Instant exp = iat.plusSeconds(expirationInSec);

        return JWT.create()
                .withHeader(headerClaims)
                .withIssuer(issuer)
                .withIssuedAt(iat)
                .withExpiresAt(exp)
                .withPayload(serializer.apply(payload))
                .sign(algorithm);
    }
}
