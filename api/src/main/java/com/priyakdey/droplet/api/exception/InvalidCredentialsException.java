package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class InvalidCredentialsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -893108334193657122L;

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
