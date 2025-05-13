package com.priyakdey.droplet.api.repository.v1;

import com.priyakdey.droplet.api.entity.v1.File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Priyak Dey
 */
@Repository
public interface FileRepository extends MongoRepository<File, Long> {
}
