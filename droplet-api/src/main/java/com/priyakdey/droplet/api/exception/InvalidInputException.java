package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class InvalidInputException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2952074446128689382L;

    public InvalidInputException(String message) {
        super(message);
    }
}
