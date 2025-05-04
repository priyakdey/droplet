package com.priyakdey.droplet.api.model.response;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class NewAccountResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 2826894837035111069L;

    private Long id;
    private String name;
    private String token;

    public NewAccountResponse() {
    }

    public NewAccountResponse(Long id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
