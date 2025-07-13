package com.priyakdey.droplet.model.dto;

import com.priyakdey.droplet.entity.Profile;

/**
 * @author Priyak Dey
 */
public record ProfileDto(String profileId, String name, String container, String avatarUrl,
                         String preferredTz) {

    public static ProfileDto from(Profile profile) {
        // TODO: add support for timezone
        return new ProfileDto(profile.getId().toString(), profile.getName(), profile.getContainer(),
                profile.getAvatarUrl(), "");
    }

}
