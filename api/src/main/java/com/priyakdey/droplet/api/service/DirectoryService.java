package com.priyakdey.droplet.api.service;

import com.priyakdey.droplet.api.dto.DirectoryDto;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author Priyak Dey
 */
public interface DirectoryService {

    ObjectId createHomeDir(long ownerId);

    List<DirectoryDto> getDirHierarchy(long ownerId);

    DirectoryDto createDir(String name, ObjectId parentId, long ownerId);

}
