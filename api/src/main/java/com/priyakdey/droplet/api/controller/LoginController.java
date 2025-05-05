package com.priyakdey.droplet.api.controller;

import com.priyakdey.droplet.api.dto.AccountDto;
import com.priyakdey.droplet.api.model.request.LoginRequest;
import com.priyakdey.droplet.api.model.response.LoginResponse;
import com.priyakdey.droplet.api.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/v1/login", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class LoginController {

    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(AuthenticationService authenticationService,
                           BCryptPasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // TODO: add validations for email
        String email = loginRequest.getEmail();
        char[] password = loginRequest.getPassword();

        AccountDto dto = authenticationService.authenticate(email, password);
        LoginResponse loginResponse = new LoginResponse(dto.id(), dto.name(), dto.token());
        return ResponseEntity.ok(loginResponse);
    }

}
