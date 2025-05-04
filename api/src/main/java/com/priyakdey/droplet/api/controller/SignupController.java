package com.priyakdey.droplet.api.controller;

import com.priyakdey.droplet.api.model.request.NewAccountRequest;
import com.priyakdey.droplet.api.model.response.NewAccountResponse;
import com.priyakdey.droplet.api.service.AuthenticationService;
import com.priyakdey.droplet.api.service.SignupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/signup", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class SignupController {

    private final SignupService signupService;
    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SignupController(SignupService signupService,
                            AuthenticationService authenticationService,
                            BCryptPasswordEncoder passwordEncoder) {
        this.signupService = signupService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<NewAccountResponse> signup(
            @RequestBody NewAccountRequest newAccountRequest) {
        String name = newAccountRequest.getName();
        String email = newAccountRequest.getEmail();

        String passwordHash = passwordEncoder.encode(new String(newAccountRequest.getPassword()));
        newAccountRequest.clearPassword();

        long id = signupService.signup(name, email, passwordHash);

        String token = authenticationService.generateToken(id);

        NewAccountResponse newAccountResponse = new NewAccountResponse(id, name, token);
        return ResponseEntity.created(URI.create("/v1/users/" + id)).body(newAccountResponse);
    }

}
