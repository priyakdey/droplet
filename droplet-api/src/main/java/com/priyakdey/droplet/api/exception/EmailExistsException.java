package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class EmailExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3607958057868891900L;

    public EmailExistsException(String message) {
        super(message);
    }
}
