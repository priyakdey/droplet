package com.priyakdey.droplet.api.validator;

import com.priyakdey.droplet.api.model.request.v1.SignupRequest;

import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.priyakdey.droplet.api.validator.SignupRequestValidator.SignupValidationResult;
import static com.priyakdey.droplet.api.validator.SignupRequestValidator.SignupValidationResultType.*;

/**
 * @author Priyak Dey
 */
public interface SignupRequestValidator
        extends Function<SignupRequest, SignupValidationResult> {

    // https://github.com/colinhacks/zod/blob/e62341b1aaf720709ee5f31785db25d5c0491659/src/types.ts#L648
    Pattern EMAIL_PATTERN_PATTERN =
            Pattern.compile("^(?!\\.)(?!.*\\.\\.)([A-Z0-9_'+\\-\\.]*[A-Z0-9_+\\-])@([A-Z0-9][A-Z0-9\\-]*\\.)+[A-Z]{2,}$",
                    Pattern.CASE_INSENSITIVE);

    Set<Character> ALLOWED_SPECIAL_CHARS = Set.of(
            '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+',
            '-', '=', '[', ']', '{', '}', '|', ';', ':', '\'', '"', ',',
            '.', '<', '>', '?', '/', '`', '~', '\\'
    );


    SignupValidationResult SUCCESS =
            new SignupValidationResult(SignupValidationResultType.SUCCESS, null);

    enum SignupValidationResultType {
        INVALID_NAME,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        SUCCESS
    }

    record SignupValidationResult(SignupValidationResultType type, String message) {
    }

    static SignupRequestValidator isValidName() {
        return req -> {
            String name = req.getName();
            if (name == null || name.isBlank()) {
                return new SignupValidationResult(INVALID_NAME, "Name cannot be blank");
            } else if (name.length() > 255) {
                return new SignupValidationResult(INVALID_NAME, "Name is too long");
            }

            return SUCCESS;
        };
    }

    static SignupRequestValidator isValidEmail() {
        return req -> {
            String email = req.getEmail();
            if (email == null || email.isBlank()) {
                return new SignupValidationResult(INVALID_EMAIL, "Email cannot be blank");
            } else if (email.length() > 254) {
                // See: https://datatracker.ietf.org/doc/html/rfc5321#section-4.5.3.1
                return new SignupValidationResult(INVALID_EMAIL, "Email is too long");
            } else if (!EMAIL_PATTERN_PATTERN.matcher(email).matches()) {
                return new SignupValidationResult(INVALID_EMAIL, "Invalid email format");
            }

            return SUCCESS;
        };
    }

    static SignupRequestValidator isValidPassword() {
        return req -> {
            char[] data = req.getPassword().getData();
            if (data == null || data.length < 8 || data.length > 20) {
                return new SignupValidationResult(INVALID_PASSWORD,
                        "Password must be between 8 and 20 chars long");
            }

            boolean hasUpperCase = false;
            boolean hasLowerCase = false;
            boolean hasDigits = false;
            boolean hasSpecialChar = false;

            for (char ch : data) {
                if (ch >= 'A' && ch <= 'Z') hasUpperCase = true;
                else if (ch >= 'a' && ch <= 'z') hasLowerCase = true;
                else if (ch >= '0' && ch <= '9') hasDigits = true;
                else if (ALLOWED_SPECIAL_CHARS.contains(ch)) hasSpecialChar = true;
            }

            if (!hasUpperCase || !hasLowerCase || !hasDigits || !hasSpecialChar) {
                return new SignupValidationResult(INVALID_PASSWORD,
                        "Password must uppercase, lowercase, digit and special character: "
                                + ALLOWED_SPECIAL_CHARS);
            }

            return SUCCESS;
        };
    }

    default SignupRequestValidator and(SignupRequestValidator next) {
        return req -> {
            SignupValidationResult result = this.apply(req);
            if (result.type != SignupValidationResultType.SUCCESS) {
                return result;
            }

            return next.apply(req);
        };
    }


}
