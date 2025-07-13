package com.priyakdey.droplet.controller;


import com.priyakdey.droplet.cache.Cache;
import com.priyakdey.droplet.exception.AuthException;
import com.priyakdey.droplet.model.response.GithubOAuthResponse;
import com.priyakdey.droplet.model.response.GithubUserInfoResponse;
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

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping("/api/auth/github")
public class GithubLoginController extends LoginController<GithubUserInfoResponse> {

    private static final Logger logger = LoggerFactory.getLogger(GithubLoginController.class);

    public GithubLoginController(
            @Qualifier("githubOAuthStateTokenService") TokenService<StatePayload> tokenService,
            ClientRegistrationRepository clientRegistrationRepository,
            @Qualifier("githubAuthServiceImpl") AuthService<GithubUserInfoResponse> authService,
            CookieService cookieService,
            TokenProperties tokenProperties,
            RestClient restClient,
            Cache<String, StatePayload> stateCache,
            @Value("${frontend.base-url}") String frontendBaseUrl) {
        super(tokenService, clientRegistrationRepository, authService, cookieService,
                tokenProperties, restClient, stateCache, frontendBaseUrl);
    }

    @Override
    @GetMapping("/login")
    public ResponseEntity<Void> redirectToProviderLogin() {
        URI githubLoginUri = buildLoginUriForProvider("github");
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(githubLoginUri)
                .build();
    }

    @Override
    @GetMapping("/callback")
    public ResponseEntity<Void> handleCallback(@RequestParam("code") String code,
                                               @RequestParam("state") String state) {
        validateState(state);
        URI accessTokenUri = buildAccessTokenUriForProvider("github", code);

        ResponseEntity<GithubOAuthResponse> accessTokenResponse = restClient.post()
                .uri(accessTokenUri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .retrieve()
                .toEntity(GithubOAuthResponse.class);

        HttpStatusCode statusCode = accessTokenResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !accessTokenResponse.hasBody()) {
            logger.error("Could not get access_token from google. Error code: {} | Body: {}",
                    statusCode, accessTokenResponse.getBody());
            throw new AuthException();
        }

        String accessToken = accessTokenResponse.getBody().getAccessToken();

        String userInfoUri = clientRegistrationRepository.findByRegistrationId("github")
                .getProviderDetails().getUserInfoEndpoint().getUri();

        ResponseEntity<GithubUserInfoResponse> userInfoResponseResponse = restClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.ACCEPT, "application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .toEntity(GithubUserInfoResponse.class);

        statusCode = userInfoResponseResponse.getStatusCode();
        if (!statusCode.is2xxSuccessful() || !accessTokenResponse.hasBody()) {
            logger.error("Could not get user info from github. Error code: {} | Body: {}",
                    statusCode, userInfoResponseResponse.getBody());
            throw new AuthException();
        }

        GithubUserInfoResponse body = userInfoResponseResponse.getBody();
        ObjectId id = authService.login(body);
        return redirectToHomeWithCookie(id.toString());
    }
}
