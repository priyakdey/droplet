package com.priyakdey.droplet.api.validator;

import com.priyakdey.droplet.api.model.request.v1.NewAccountRequest;

import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResult;
import static com.priyakdey.droplet.api.validator.NewAccountRequestValidator.NewAccountValidationResultType.*;

/**
 * @author Priyak Dey
 */
public interface NewAccountRequestValidator
        extends Function<NewAccountRequest, NewAccountValidationResult> {

    // https://github.com/colinhacks/zod/blob/e62341b1aaf720709ee5f31785db25d5c0491659/src/types.ts#L648
    Pattern EMAIL_PATTERN_PATTERN =
            Pattern.compile("^(?!\\.)(?!.*\\.\\.)([A-Z0-9_'+\\-\\.]*[A-Z0-9_+\\-])@([A-Z0-9][A-Z0-9\\-]*\\.)+[A-Z]{2,}$",
                    Pattern.CASE_INSENSITIVE);

    Set<Character> ALLOWED_SPECIAL_CHARS = Set.of(
            '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+',
            '-', '=', '[', ']', '{', '}', '|', ';', ':', '\'', '"', ',',
            '.', '<', '>', '?', '/', '`', '~', '\\'
    );


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

    static NewAccountRequestValidator isValidPassword() {
        return req -> {
            char[] data = req.getPassword().getData();
            if (data == null || data.length < 8 || data.length > 20) {
                return new NewAccountValidationResult(INVALID_PASSWORD,
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
                return new NewAccountValidationResult(INVALID_PASSWORD,
                        "Password must contain at-least 1 uppercase, 1 lowercase, 1 digit and 1 special char - " + ALLOWED_SPECIAL_CHARS);
            }

            return SUCCESS;
        };
    }

    default NewAccountRequestValidator and(NewAccountRequestValidator next) {
        return req -> {
            NewAccountValidationResult result = this.apply(req);
            if (result.type != NewAccountValidationResultType.SUCCESS) {
                return result;
            }

            return next.apply(req);
        };
    }


}
