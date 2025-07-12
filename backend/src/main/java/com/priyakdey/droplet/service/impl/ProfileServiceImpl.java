package com.priyakdey.droplet.service.impl;

import com.priyakdey.droplet.entity.Profile;
import com.priyakdey.droplet.exception.ProfileNotFoundException;
import com.priyakdey.droplet.model.dto.NewProfileDto;
import com.priyakdey.droplet.repository.ProfileRepository;
import com.priyakdey.droplet.service.ProfileService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public ObjectId save(NewProfileDto newProfileDto) {
        Profile profile = newProfileDto.toProfile();
        return profileRepository.save(profile).getId();
    }

    @Override
    public boolean existsByProviderId(String providerId) {
        return profileRepository.existsByProviderId(providerId);
    }

    @Override
    public ObjectId getByProviderId(String providerId) {
        return profileRepository
                .findByProviderId(providerId)
                .orElseThrow(() -> {
                    logger.error("Profile not found by provider id {}", providerId);
                    return new ProfileNotFoundException();
                })
                .getId();
    }
}
