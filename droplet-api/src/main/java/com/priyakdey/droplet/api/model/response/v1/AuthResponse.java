package com.priyakdey.droplet.api.model.response.v1;

import com.priyakdey.droplet.api.model.dto.v1.ProfileMetadataDto;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Priyak Dey
 */
public class AuthResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -5271059411625841031L;

    private ProfileMetadataDto profile;
    private String token;

    public ProfileMetadataDto getProfile() {
        return profile;
    }

    public void setProfile(ProfileMetadataDto profile) {
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
