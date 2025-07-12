package com.priyakdey.droplet.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.priyakdey.droplet.model.response.GithubOAuthResponse;
import com.priyakdey.droplet.model.response.GithubUserInfoResponse;
import com.priyakdey.droplet.security.SessionPayload;
import com.priyakdey.droplet.security.StatePayload;
import com.priyakdey.droplet.security.TokenProperties;
import com.priyakdey.droplet.security.service.TokenService;
import com.priyakdey.droplet.service.AuthService;
import com.priyakdey.droplet.service.CookieService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping("/api/auth/github")
public class GithubLoginController implements LoginController {

    private final TokenService<StatePayload> tokenService;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AuthService<GithubUserInfoResponse> authService;
    private final CookieService cookieService;
    private final TokenProperties tokenProperties;
    private final RestClient restClient;

    public GithubLoginController(
            @Qualifier("githubOAuthStateTokenService") TokenService<StatePayload> tokenService,
            ClientRegistrationRepository clientRegistrationRepository,
            @Qualifier("githubAuthServiceImpl") AuthService<GithubUserInfoResponse> authService,
            CookieService cookieService,
            TokenProperties tokenProperties,
            RestClient restClient) {
        this.tokenService = tokenService;
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authService = authService;
        this.cookieService = cookieService;
        this.tokenProperties = tokenProperties;
        this.restClient = restClient;
    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<Void> redirectToProviderLogin() {
        String nonce = UUID.randomUUID().toString();
        String state = tokenService.generate(new StatePayload(nonce, Instant.now().toEpochMilli()));

        ClientRegistration github = clientRegistrationRepository.findByRegistrationId("github");
        String clientName = github.getClientName();
        String clientId = github.getClientId();
        String redirectUri = github.getRedirectUri();
        String scope = String.join(" ", github.getScopes());
        String authorizationUri = github.getProviderDetails().getAuthorizationUri();

        Map<String, Object> params = Map.of(
                "state", state,
                "client_name", clientName,
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "response_type", "code",
                "scope", scope,
                "prompt", "select_account"
        );

        URI githubLoginUri = buildUri(authorizationUri, params);

        return ResponseEntity.status(HttpStatus.FOUND).location(githubLoginUri).build();
    }

    @Override
    @GetMapping("/callback")
    public ResponseEntity<Void> handleCallback(@RequestParam("code") String code,
                                               @RequestParam("state") String state) {
        try {
            tokenService.decode(state);     // TODO: we need a way to verify the nonce sent.
        } catch (JWTVerificationException e) {
            throw new RuntimeException("JWT verification failed");  // TODO: custom exceptions
        }

        ClientRegistration github = clientRegistrationRepository.findByRegistrationId("github");
        String clientName = github.getClientName();
        String clientId = github.getClientId();
        String clientSecret = github.getClientSecret();
        String redirectUri = github.getRedirectUri();

        String tokenUri = github.getProviderDetails().getTokenUri();

        Map<String, Object> params = Map.of(
                "client_name", clientName,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "code", code,
                "grant_type", "authorization_code"
        );

        URI uri = buildUri(tokenUri, params);

        ResponseEntity<GithubOAuthResponse> accessTokenResponse = restClient.post()
                .uri(uri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .retrieve()     // TODO: handle error
                .toEntity(GithubOAuthResponse.class);

        HttpStatusCode statusCode = accessTokenResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !accessTokenResponse.hasBody()) {
            // TODO: custom exception
            throw new RuntimeException("Failed to exchange code for token for google. Response code: " + statusCode);
        }

        String accessToken = accessTokenResponse.getBody().getAccessToken();

        String userInfoUri = github.getProviderDetails().getUserInfoEndpoint().getUri();
        ResponseEntity<GithubUserInfoResponse> userInfoResponseResponse = restClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(GithubUserInfoResponse.class);
        statusCode = userInfoResponseResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !accessTokenResponse.hasBody()) {
            // TODO: custom exception
            throw new RuntimeException("Failed to exchange code for token for google. Response code: " + statusCode);
        }

        GithubUserInfoResponse body = userInfoResponseResponse.getBody();
        ObjectId id = authService.login(body);

        Instant iat = Instant.now();
        TokenProperties.Token jwtProps = tokenProperties.getJwt();
        Instant eat = iat.plusSeconds(jwtProps.expirationInSec());

        SessionPayload payload = new SessionPayload(jwtProps.issuer(), iat, eat, id.toString(),
                jwtProps.expirationInSec());
        ResponseCookie cookie = cookieService.getCookie(payload);

        URI homePage = URI.create("http://localhost:5173/home");        // TODO: env driven
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.SET_COOKIE, cookie.getValue()).location(homePage).build();
    }
}
