package com.priyakdey.droplet.api.model.request;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Priyak Dey
 */
public class NewAccountRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1333621414711069861L;

    private String name;
    private String email;
    private char[] password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void clearPassword() {
        if (password != null) {
            Arrays.fill(password, '\0');
        }
    }

}
