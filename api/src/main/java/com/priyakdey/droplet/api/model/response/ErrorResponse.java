package com.priyakdey.droplet.api.model.response;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1477878950319590240L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
