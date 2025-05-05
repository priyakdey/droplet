package com.priyakdey.droplet.api.repository;

import com.priyakdey.droplet.api.entity.Directory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Priyak Dey
 */
@Repository
public interface DirectoryRepository extends MongoRepository<Directory, ObjectId> {
}
