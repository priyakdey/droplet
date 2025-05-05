package com.priyakdey.droplet.api.model.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Priyak Dey
 */
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 451564968886249284L;

    private String email;

    private char[] password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

}
