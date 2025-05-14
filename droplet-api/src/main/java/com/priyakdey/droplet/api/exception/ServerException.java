package com.priyakdey.droplet.api.exception;

import java.io.Serial;

/**
 * @author Priyak Dey
 */
public class ServerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -3027606466061705758L;

    public ServerException(String message) {
        super(message);
    }
}
