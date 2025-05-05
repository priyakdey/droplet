package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class AccountDoesNotExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 7929126604817002590L;

    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
