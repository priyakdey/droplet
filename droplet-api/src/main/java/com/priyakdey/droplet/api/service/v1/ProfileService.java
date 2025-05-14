package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.entity.v1.Profile;

/**
 * @author Priyak Dey
 */
public interface ProfileService {

    Profile create(String name, String timeZone, Account account);

    void deleteById(Integer id);
}
