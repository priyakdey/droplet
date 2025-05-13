package com.priyakdey.droplet.api.repository.v1;

import com.priyakdey.droplet.api.entity.v1.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Priyak Dey
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
