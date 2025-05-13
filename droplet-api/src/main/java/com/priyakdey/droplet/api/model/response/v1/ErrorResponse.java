package com.priyakdey.droplet.api.model.response.v1;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class ErrorResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -528805153320401417L;

    private int statusCode;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
