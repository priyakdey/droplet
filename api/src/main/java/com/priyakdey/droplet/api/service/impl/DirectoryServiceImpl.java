package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.entity.Directory;
import com.priyakdey.droplet.api.repository.DirectoryRepository;
import com.priyakdey.droplet.api.service.DirectoryService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {

    private final DirectoryRepository directoryRepository;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public ObjectId createHomeDir(long ownerId) {
        Directory homeDir = new Directory();
        homeDir.setOwnerId(ownerId);
        homeDir.setName("home");
        homeDir.setParentId(null);
        homeDir = directoryRepository.save(homeDir);
        return homeDir.getId();
    }
}
