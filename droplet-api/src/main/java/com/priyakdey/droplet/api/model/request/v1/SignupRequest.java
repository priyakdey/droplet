package com.priyakdey.droplet.api.model.request.v1;

import com.priyakdey.droplet.api.security.core.SecureCharSequence;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class SignupRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1344679194305946621L;

    private String name;

    private String email;

    private SecureCharSequence password;

    private String timezone;

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

    public SecureCharSequence getPassword() {
        return password;
    }

    public void setPassword(SecureCharSequence password) {
        this.password = password;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
