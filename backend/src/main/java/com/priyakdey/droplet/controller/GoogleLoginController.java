package com.priyakdey.droplet.controller;


import com.priyakdey.droplet.cache.Cache;
import com.priyakdey.droplet.exception.AuthException;
import com.priyakdey.droplet.model.response.GoogleOAuthResponse;
import com.priyakdey.droplet.security.payload.StatePayload;
import com.priyakdey.droplet.security.config.TokenProperties;
import com.priyakdey.droplet.security.jwt.TokenService;
import com.priyakdey.droplet.service.AuthService;
import com.priyakdey.droplet.service.CookieService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/api/auth/google")
public class GoogleLoginController extends LoginController<String> {
    private static final Logger logger = LoggerFactory.getLogger(GoogleLoginController.class);

    public GoogleLoginController(
            @Qualifier("googleOAuthStateTokenService") TokenService<StatePayload> stateTokenService,
            ClientRegistrationRepository clientRegistrationRepository,
            @Qualifier("googleAuthServiceImpl") AuthService<String> authService,
            CookieService cookieService,
            TokenProperties tokenProperties,
            RestClient restClient,
            Cache<String, StatePayload> stateCache,
            @Value("${frontend.base-url}") String frontendBaseUrl) {
        super(stateTokenService, clientRegistrationRepository, authService, cookieService,
                tokenProperties, restClient, stateCache, frontendBaseUrl);
    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<Void> redirectToProviderLogin() {
        URI googleLoginUri = buildLoginUriForProvider("google");
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(googleLoginUri)
                .build();
    }

    @Override
    @GetMapping("/callback")
    public ResponseEntity<Void> handleCallback(@RequestParam("code") String code,
                                               @RequestParam("state") String state) {
        validateState(state);
        URI accessTokenUri = buildAccessTokenUriForProvider("google", code);

        ResponseEntity<GoogleOAuthResponse> response = restClient.post()
                .uri(accessTokenUri)
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve()
                .toEntity(GoogleOAuthResponse.class);

        HttpStatusCode statusCode = response.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !response.hasBody()) {
            logger.error("Could not get access_token from google. Error code: {} | Body: {}",
                    statusCode, response.getBody());
            throw new AuthException();
        }

        String idToken = response.getBody().getIdToken();
        ObjectId id = authService.login(idToken);
        return redirectToHomeWithCookie(id.toString());
    }

}
