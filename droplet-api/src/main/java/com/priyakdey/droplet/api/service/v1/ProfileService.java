package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.entity.v1.Profile;

import java.util.Optional;

/**
 * @author Priyak Dey
 */
public interface ProfileService {

    Profile create(String name, String timezone, Account account);

    Optional<Profile> getProfile(Account account);

    void deleteById(Integer id);
}
