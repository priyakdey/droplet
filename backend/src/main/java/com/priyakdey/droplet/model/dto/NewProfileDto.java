package com.priyakdey.droplet.model.dto;

import com.priyakdey.droplet.entity.Profile;

/**
 * @author Priyak Dey
 */
public record NewProfileDto(String name, String email, String avatarUrl,
                            String provider, String providerId) {

    public Profile toProfile() {
        Profile profile = new Profile();
        profile.setProvider(provider);
        profile.setProviderId(providerId);
        profile.setName(name);
        profile.setEmail(email);
        return profile;
    }

}
