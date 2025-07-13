package com.priyakdey.droplet.model.response;

import com.priyakdey.droplet.model.dto.ProfileDto;

/**
 * @author Priyak Dey
 */
public class ProfileDetailsResponse {

    private String profileId;
    private String name;
    private String container;
    private String avatarUrl;

    public ProfileDetailsResponse() {
    }

    public ProfileDetailsResponse(ProfileDto profileDto) {
        this.profileId = profileDto.profileId();
        this.name = profileDto.name();
        this.container = profileDto.container();
        this.avatarUrl = profileDto.avatarUrl();
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
