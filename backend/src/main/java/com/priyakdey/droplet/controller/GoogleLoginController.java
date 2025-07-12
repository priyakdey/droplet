package com.priyakdey.droplet.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.priyakdey.droplet.model.response.GoogleOAuthResponse;
import com.priyakdey.droplet.security.SessionPayload;
import com.priyakdey.droplet.security.StatePayload;
import com.priyakdey.droplet.security.TokenProperties;
import com.priyakdey.droplet.security.service.TokenService;
import com.priyakdey.droplet.service.AuthService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/api/auth/google")
public class GoogleLoginController {

    private final TokenService<StatePayload> tokenService;
    private final TokenService<SessionPayload> sessionTokenService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthService authService;
    private final TokenProperties tokenProperties;

    @Autowired
    private RestClient restClient;

    public GoogleLoginController(
            @Qualifier("googleOAuthStateTokenService") TokenService<StatePayload> stateTokenService,
            @Qualifier("sessionTokenService") TokenService<SessionPayload> sessionTokenService,
            ClientRegistrationRepository clientRegistrationRepository,
            @Qualifier("googleAuthServiceImpl") AuthService authService,
            TokenProperties tokenProperties) {
        this.tokenService = stateTokenService;
        this.sessionTokenService = sessionTokenService;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authService = authService;
        this.tokenProperties = tokenProperties;
    }

    @GetMapping("/login")
    public ResponseEntity<Void> redirectToGoogleLogin() {
        String nonce = UUID.randomUUID().toString();
        String state = tokenService.generate(new StatePayload(nonce, Instant.now().toEpochMilli()));

        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        String clientName = google.getClientName();
        String clientId = google.getClientId();
        String redirectUri = google.getRedirectUri();
        String scope = String.join(" ", google.getScopes());
        String authorizationUri = google.getProviderDetails().getAuthorizationUri();

        Map<String, Object> params = Map.of(
                "state", state,
                "client_name", clientName,
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "response_type", "code",
                "scope", scope,
                "prompt", "select_account"
        );

        URI googleLoginUri = buildUri(authorizationUri, params);

        return ResponseEntity.status(HttpStatus.FOUND).location(googleLoginUri).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> handleCallback(@RequestParam("code") String code,
                                               @RequestParam("state") String state) {
        try {
            tokenService.decode(state);     // TODO: we need a way to verify the nonce sent.
        } catch (JWTVerificationException e) {
            throw new RuntimeException("JWT verification failed");  // TODO: custom exceptions
        }

        ClientRegistration google = clientRegistrationRepository.findByRegistrationId("google");
        String clientName = google.getClientName();
        String clientId = google.getClientId();
        String clientSecret = google.getClientSecret();
        String redirectUri = google.getRedirectUri();

        String tokenUri = google.getProviderDetails().getTokenUri();

        Map<String, Object> params = Map.of(
                "client_name", clientName,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "code", code,
                "grant_type", "authorization_code"
        );

        URI uri = buildUri(tokenUri, params);

        ResponseEntity<GoogleOAuthResponse> response = restClient.post()
                .uri(uri)
                .header("Content-Type", APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()     // TODO: handle error
                .toEntity(GoogleOAuthResponse.class);

        HttpStatusCode statusCode = response.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !response.hasBody()) {
            // TODO: custom exception
            throw new RuntimeException("Failed to exchange code for token for google. Response code: " + statusCode);
        }

        String idToken = response.getBody().getIdToken();
        ObjectId id = authService.login(idToken);

        Instant iat = Instant.now();
        TokenProperties.Token jwtProps = tokenProperties.getJwt();
        Instant eat = iat.plusSeconds(jwtProps.expirationInSec());

        SessionPayload payload = new SessionPayload(jwtProps.issuer(), iat, eat, id.toString());
        String token = sessionTokenService.generate(payload);

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .path("/")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .maxAge(3600)
                .build();

        URI homePage = URI.create("http://localhost:5173/home");
        return ResponseEntity.status(HttpStatus.FOUND).location(homePage)
                .header(HttpHeaders.SET_COOKIE, cookie.getValue()).build();
    }

    private URI buildUri(String uri, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            builder.queryParam(key, val);
        }

        return builder.build().toUri();
    }

}
