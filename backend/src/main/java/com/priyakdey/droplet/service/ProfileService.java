package com.priyakdey.droplet.service;

import com.priyakdey.droplet.model.dto.NewProfileDto;
import com.priyakdey.droplet.model.dto.ProfileDto;
import org.bson.types.ObjectId;

/**
 * @author Priyak Dey
 */
public interface ProfileService {

    ObjectId save(NewProfileDto newProfileDto);

    boolean existsByProviderId(String providerId);

    ObjectId getByProviderId(String providerId);

    ProfileDto getByProfileId(String profileId);
}
