package com.priyakdey.droplet.api.controller.v1;

import com.priyakdey.droplet.api.model.request.v1.LoginRequest;
import com.priyakdey.droplet.api.model.response.v1.AuthResponse;
import com.priyakdey.droplet.api.security.core.SecureCharSequence;
import com.priyakdey.droplet.api.service.v1.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/login", produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
public class LoginController {

    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(AuthenticationService authenticationService,
                           PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        SecureCharSequence password = loginRequest.getPassword();
        AuthResponse authResponse = authenticationService.authenticate(email, password);
        password.clear();
        return ResponseEntity.ok(authResponse);
    }
}
