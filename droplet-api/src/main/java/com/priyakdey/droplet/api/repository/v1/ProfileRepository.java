package com.priyakdey.droplet.api.repository.v1;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.entity.v1.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Priyak Dey
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByAccount(Account account);

}
