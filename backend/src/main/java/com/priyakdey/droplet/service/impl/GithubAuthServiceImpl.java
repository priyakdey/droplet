package com.priyakdey.droplet.service.impl;

import com.priyakdey.droplet.model.dto.NewProfileDto;
import com.priyakdey.droplet.model.response.GithubUserInfoResponse;
import com.priyakdey.droplet.service.AuthService;
import com.priyakdey.droplet.service.ProfileService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class GithubAuthServiceImpl implements AuthService<GithubUserInfoResponse> {
    private static final String PROVIDER_NAME = "github";
    private static final String PROVIDER_ID_FMT = "github|%s";

    private final ProfileService profileService;

    public GithubAuthServiceImpl(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public ObjectId login(GithubUserInfoResponse userInfo) {
        String providerId = String.format(PROVIDER_ID_FMT, userInfo.getId());
        if (profileService.existsByProviderId(providerId)) {
            return profileService.getByProviderId(providerId);
        }

        NewProfileDto dto = new NewProfileDto(userInfo.getName(), userInfo.getEmail(),
                userInfo.getAvatarUrl(), PROVIDER_NAME, providerId);
        return profileService.save(dto);
    }
}
