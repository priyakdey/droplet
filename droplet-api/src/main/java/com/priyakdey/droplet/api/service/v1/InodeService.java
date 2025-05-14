package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.entity.v1.Directory;
import org.bson.types.ObjectId;

/**
 * @author Priyak Dey
 */
public interface InodeService {

    Directory createHomeDir(Integer ownerId);

    void deleteById(String id);

    void deleteById(ObjectId id);

}
