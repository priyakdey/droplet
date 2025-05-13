package com.priyakdey.droplet.api.validator;

import com.priyakdey.droplet.api.model.request.v1.NewAccountRequest;

import java.util.function.Function;
import java.util.regex.Pattern;

import static com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResult;
import static com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResultType.INVALID_EMAIL;
import static com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResultType.INVALID_NAME;

/**
 * @author Priyak Dey
 */
public interface NewAccountRequestValidator
        extends Function<NewAccountRequest, NewAccountValidationResult> {

    // from: https://github.com/colinhacks/zod/blob/e62341b1aaf720709ee5f31785db25d5c0491659/src/types.ts#L648
    String EMAIL_PATTERN = "^(?!\\.)(?!.*\\.\\.)([A-Z0-9_'+\\-\\.]*[A-Z0-9_+\\-])@([A-Z0-9][A-Z0-9\\-]*\\.)+[A-Z]{2,}$";
    Pattern EMAIL_PATTERN_PATTERN = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    NewAccountValidationResult SUCCESS =
            new NewAccountValidationResult(NewAccountValidationResultType.SUCCESS, null);

    enum NewAccountValidationResultType {
        INVALID_NAME,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        SUCCESS
    }

    record NewAccountValidationResult(NewAccountValidationResultType type, String message) {
    }

    static NewAccountRequestValidator isValidName() {
        return req -> {
            String name = req.getName();
            if (name == null || name.isBlank()) {
                return new NewAccountValidationResult(INVALID_NAME, "Name cannot be blank");
            } else if (name.length() > 255) {
                return new NewAccountValidationResult(INVALID_NAME, "Name is too long");
            }

            return SUCCESS;
        };
    }

    static NewAccountRequestValidator isValidEmail() {
        return req -> {
            String email = req.getEmail();
            if (email == null || email.isBlank()) {
                return new NewAccountValidationResult(INVALID_EMAIL, "Email cannot be blank");
            } else if (email.length() > 254) {
                // See: https://datatracker.ietf.org/doc/html/rfc5321#section-4.5.3.1
                return new NewAccountValidationResult(INVALID_EMAIL, "Email is too long");
            } else if (!EMAIL_PATTERN_PATTERN.matcher(email).matches()) {
                return new NewAccountValidationResult(INVALID_EMAIL, "Invalid email format");
            }

            return SUCCESS;
        };
    }

    // static NewAccountRequestValidator isValidPassword() {
    //     return req -> {
    //
    //     }
    // }


}
