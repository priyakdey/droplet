package com.priyakdey.droplet.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.droplet.security.*;
import com.priyakdey.droplet.security.service.Hmac256Signer;
import com.priyakdey.droplet.security.service.Hmac256Verifier;
import com.priyakdey.droplet.security.service.TokenService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Priyak Dey
 */
@Configuration
@EnableConfigurationProperties({TokenProperties.class})
public class TokenConfiguration {

    @Bean("googleOAuthStateTokenService")
    public TokenService<StatePayload> googleOAuthStateTokenService(TokenProperties tokenProperties) {
        TokenProperties.Token provider = tokenProperties.getState().getProviders().get("google");
        byte[] secret = provider.secret();
        String issuer = provider.issuer();
        int expirationInSec = provider.expirationInSec();
        int leewayInSec = provider.leewayInSec();

        Function<StatePayload, Map<String, ?>> serializer = payload ->
                Map.of("nonce", payload.nonce(), "ts", payload.timestamp());
        Function<DecodedJWT, StatePayload> deserializer = decodedJwt ->
                new StatePayload(decodedJwt.getClaim("nonce").asString(),
                        decodedJwt.getClaim("ts").asLong());

        TokenSigner<StatePayload> signer =
                new Hmac256Signer<>(secret, serializer, issuer, expirationInSec);
        TokenVerifier<StatePayload> verifier = new Hmac256Verifier<>(secret, deserializer, issuer,
                leewayInSec);

        Arrays.fill(secret, (byte) 0x0);
        return new TokenService<>(signer, verifier);
    }

    @Bean("githubOAuthStateTokenService")
    public TokenService<StatePayload> githubOAuthStateTokenService(TokenProperties tokenProperties) {
        TokenProperties.Token provider = tokenProperties.getState().getProviders().get("github");
        byte[] secret = provider.secret();
        String issuer = provider.issuer();
        int expirationInSec = provider.expirationInSec();
        int leewayInSec = provider.leewayInSec();

        Function<StatePayload, Map<String, ?>> serializer = payload ->
                Map.of("nonce", payload.nonce(), "ts", payload.timestamp());
        Function<DecodedJWT, StatePayload> deserializer = decodedJwt ->
                new StatePayload(decodedJwt.getClaim("nonce").asString(),
                        decodedJwt.getClaim("ts").asLong());

        TokenSigner<StatePayload> signer =
                new Hmac256Signer<>(secret, serializer, issuer, expirationInSec);
        TokenVerifier<StatePayload> verifier = new Hmac256Verifier<>(secret, deserializer, issuer,
                leewayInSec);

        Arrays.fill(secret, (byte) 0x0);
        return new TokenService<>(signer, verifier);
    }

    @Bean("sessionTokenService")
    public TokenService<SessionPayload> sessionTokenService(TokenProperties tokenProperties) {
        TokenProperties.Token jwt = tokenProperties.getJwt();
        byte[] secret = jwt.secret();
        String issuer = jwt.issuer();
        int expirationInSec = jwt.expirationInSec();
        int leewayInSec = jwt.leewayInSec();

        Function<SessionPayload, Map<String, ?>> serializer = payload ->
                Map.of("iss", payload.issuer(), "iat", payload.iat(),
                        "eat", payload.eat(), "sub", payload.sub());
        Function<DecodedJWT, SessionPayload> deserializer = decodedJwt ->
                new SessionPayload(decodedJwt.getIssuer(), decodedJwt.getIssuedAtAsInstant(),
                        decodedJwt.getExpiresAtAsInstant(), decodedJwt.getSubject(), expirationInSec);

        TokenSigner<SessionPayload> signer =
                new Hmac256Signer<>(secret, serializer, issuer, expirationInSec);
        TokenVerifier<SessionPayload> verifier = new Hmac256Verifier<>(secret, deserializer, issuer,
                leewayInSec);

        Arrays.fill(secret, (byte) 0x0);
        return new TokenService<>(signer, verifier);
    }


}
