package com.priyakdey.droplet.api.model.request.v1;

import com.priyakdey.droplet.api.security.core.SecureCharSequence;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1576594103832812798L;

    private String email;
    private SecureCharSequence password;

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
}
