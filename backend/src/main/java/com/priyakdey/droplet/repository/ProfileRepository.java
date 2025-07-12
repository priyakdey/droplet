package com.priyakdey.droplet.repository;

import com.priyakdey.droplet.entity.Profile;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Priyak Dey
 */
@Repository
public interface ProfileRepository extends CrudRepository<Profile, ObjectId> {

    Optional<Profile> findByProviderId(String providerId);

    boolean existsByProviderId(String providerId);

}
