package com.priyakdey.droplet.api.service.v1.impl;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.entity.v1.Profile;
import com.priyakdey.droplet.api.repository.v1.ProfileRepository;
import com.priyakdey.droplet.api.service.v1.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Profile create(String name, String timeZone, Account account) {
        String containerName = "home-" + account.getId();
        Profile profile = new Profile(name, timeZone, containerName, account);
        return profileRepository.save(profile);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        profileRepository.deleteById(id);
    }
}
