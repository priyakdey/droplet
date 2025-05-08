package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class DirectoryExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4966331732808267007L;

    public DirectoryExistsException(String message) {
        super(message);
    }
}
