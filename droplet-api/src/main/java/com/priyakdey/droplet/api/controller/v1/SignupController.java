package com.priyakdey.droplet.api.controller.v1;

import com.priyakdey.droplet.api.exception.InvalidInputException;
import com.priyakdey.droplet.api.model.dto.v1.SignupDto;
import com.priyakdey.droplet.api.model.request.v1.SignupRequest;
import com.priyakdey.droplet.api.model.response.v1.AuthResponse;
import com.priyakdey.droplet.api.security.core.SecureCharSequence;
import com.priyakdey.droplet.api.service.v1.AuthenticationService;
import com.priyakdey.droplet.api.validator.SignupRequestValidator;
import com.priyakdey.droplet.api.validator.SignupRequestValidator.SignupValidationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public SignupController(PasswordEncoder passwordEncoder,
                            AuthenticationService authenticationService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest signupRequest) {
        SignupValidationResult result = SignupRequestValidator.isValidName()
                .and(SignupRequestValidator.isValidEmail())
                .and(SignupRequestValidator.isValidPassword())
                .and(SignupRequestValidator.isValidTimezone())
                .apply(signupRequest);

        if (result.type() != SignupRequestValidator.SignupValidationResultType.SUCCESS) {
            throw new InvalidInputException(result.message());
        }

        String name = signupRequest.getName();
        String email = signupRequest.getEmail();
        String timezone = signupRequest.getTimezone();
        SecureCharSequence password = signupRequest.getPassword();

        String passwordHash = passwordEncoder.encode(password);
        password.clear();


        SignupDto dto = SignupDto.from(name, email, passwordHash, timezone);
        AuthResponse authResponse = authenticationService.signup(dto);
        URI location = URI.create("/v1/profiles/" + authResponse.getProfile().profileId());
        return ResponseEntity.created(location).body(authResponse);
    }

}
