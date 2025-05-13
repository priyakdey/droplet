package com.priyakdey.droplet.api.controller.v1;

import com.priyakdey.droplet.api.exception.InvalidInputException;
import com.priyakdey.droplet.api.model.dto.v1.AccountSignupDto;
import com.priyakdey.droplet.api.model.request.v1.NewAccountRequest;
import com.priyakdey.droplet.api.security.core.SecureCharSequence;
import com.priyakdey.droplet.api.validator.NewAccountRequestValidator;
import com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResult;
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
@RequestMapping(path = "/v1/signup", consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class SignupController {

    private final PasswordEncoder passwordEncoder;

    public SignupController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String signup(@RequestBody NewAccountRequest newAccountRequest) {
        NewAccountValidationResult result = NewAccountRequestValidator.isValidName()
                .and(NewAccountRequestValidator.isValidEmail())
                .and(NewAccountRequestValidator.isValidPassword())
                .apply(newAccountRequest);

        if (result.type() != NewAccountRequestValidator.NewAccountValidationResultType.SUCCESS) {
            throw new InvalidInputException(result.message());
        }

        String name = newAccountRequest.getName();
        String email = newAccountRequest.getEmail();
        SecureCharSequence password = newAccountRequest.getPassword();

        String passwordHash = passwordEncoder.encode(password);
        password.clear();

        AccountSignupDto dto = AccountSignupDto.from(name, email, passwordHash);

        return "Signup successful!";
    }

}
