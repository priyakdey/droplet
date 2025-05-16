package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.entity.v1.Directory;
import com.priyakdey.droplet.api.model.dto.v1.DirectoryDto;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * @author Priyak Dey
 */
public interface InodeService {

    Directory createHomeDir(Integer ownerId);

    Optional<Directory> getHomeDir(Integer ownerId);

    void deleteById(String id);

    void deleteById(ObjectId id);

    List<DirectoryDto> getAllDirectories(Integer ownerId);

}
