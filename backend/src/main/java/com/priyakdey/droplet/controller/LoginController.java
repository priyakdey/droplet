package com.priyakdey.droplet.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.priyakdey.droplet.cache.Cache;
import com.priyakdey.droplet.exception.AuthException;
import com.priyakdey.droplet.security.payload.SessionPayload;
import com.priyakdey.droplet.security.payload.StatePayload;
import com.priyakdey.droplet.security.config.TokenProperties;
import com.priyakdey.droplet.security.jwt.TokenService;
import com.priyakdey.droplet.service.AuthService;
import com.priyakdey.droplet.service.CookieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Priyak Dey
 */
public abstract class LoginController<T> {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    protected final TokenService<StatePayload> tokenService;
    protected final ClientRegistrationRepository clientRegistrationRepository;
    protected final AuthService<T> authService;
    protected final CookieService cookieService;
    protected final TokenProperties tokenProperties;
    protected final RestClient restClient;
    protected final Cache<String, StatePayload> stateCache;

    protected final URI homePageUri;

    protected LoginController(TokenService<StatePayload> tokenService,
                              ClientRegistrationRepository clientRegistrationRepository,
                              AuthService<T> authService,
                              CookieService cookieService,
                              TokenProperties tokenProperties,
                              RestClient restClient,
                              Cache<String, StatePayload> stateCache,
                              String frontendBaseUrl) {
        this.tokenService = tokenService;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authService = authService;
        this.cookieService = cookieService;
        this.tokenProperties = tokenProperties;
        this.restClient = restClient;
        this.stateCache = stateCache;
        this.homePageUri = URI.create(frontendBaseUrl + "/home");
    }

    public abstract ResponseEntity<Void> redirectToProviderLogin();

    public abstract ResponseEntity<Void> handleCallback(String code, String state);

    protected String generateState() {
        String nonce = UUID.randomUUID().toString();
        StatePayload statePayload = new StatePayload(nonce, Instant.now().toEpochMilli());
        String state = tokenService.generate(statePayload).trim();
        stateCache.put(state, statePayload);
        return state;
    }

    protected void validateState(String state) {
        try {
            state = state.trim();
            StatePayload received = tokenService.decode(state);
            StatePayload stored = stateCache.get(state);
            if (stored == null || !Objects.equals(received, stored)) {
                logger.warn("Invalid or replayed state: {}", state);
                throw new JWTVerificationException("Mismatch in nonce");
            }
        } catch (JWTVerificationException e) {
            logger.error("Error decoding state: ", e);
            throw new AuthException();
        } finally {
            stateCache.evict(state);
        }
    }

    protected URI buildLoginUriForProvider(String providerCode) {
        ClientRegistration provider = clientRegistrationRepository
                .findByRegistrationId(providerCode);
        String clientName = provider.getClientName();
        String clientId = provider.getClientId();
        String redirectUri = provider.getRedirectUri();
        String scope = String.join(" ", provider.getScopes());
        String authorizationUri = provider.getProviderDetails().getAuthorizationUri();

        String state = generateState();

        Map<String, Object> params = Map.of(
                "state", state,
                "client_name", clientName,
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "response_type", "code",
                "scope", scope,
                "prompt", "select_account"
        );

        return buildUri(authorizationUri, params);
    }

    protected URI buildAccessTokenUriForProvider(String providerCode, String code) {
        ClientRegistration provider = clientRegistrationRepository
                .findByRegistrationId(providerCode);
        String clientName = provider.getClientName();
        String clientId = provider.getClientId();
        String clientSecret = provider.getClientSecret();
        String redirectUri = provider.getRedirectUri();

        String tokenUri = provider.getProviderDetails().getTokenUri();

        Map<String, Object> params = Map.of(
                "client_name", clientName,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "code", code,
                "grant_type", "authorization_code"
        );

        return buildUri(tokenUri, params);
    }

    protected URI buildUri(String uri, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            builder.queryParam(key, val);
        }

        return builder.build().toUri();
    }

    protected ResponseEntity<Void> redirectToHomeWithCookie(String profileId) {
        Instant iat = Instant.now();
        TokenProperties.Token jwtProps = tokenProperties.getJwt();
        Instant eat = iat.plusSeconds(jwtProps.expirationInSec());

        SessionPayload payload = new SessionPayload(jwtProps.issuer(), iat, eat, profileId,
                jwtProps.expirationInSec());
        ResponseCookie cookie = cookieService.create(payload);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(homePageUri)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

}
