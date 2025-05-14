package com.priyakdey.droplet.api.repository.v1;

import com.priyakdey.droplet.api.entity.v1.Directory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Priyak Dey
 */
@Repository
public interface DirectoryRepository extends MongoRepository<Directory, ObjectId> {

    Optional<Directory> findByOwnerId(Integer ownerId);

}
