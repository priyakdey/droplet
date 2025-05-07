package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.dto.DirectoryDto;
import com.priyakdey.droplet.api.entity.Directory;
import com.priyakdey.droplet.api.repository.DirectoryRepository;
import com.priyakdey.droplet.api.service.DirectoryService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * @author Priyak Dey
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {
    private static final String HOME_DIR = "HOME";

    private final DirectoryRepository directoryRepository;

    public DirectoryServiceImpl(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Override
    public ObjectId createHomeDir(long ownerId) {
        Directory homeDir = new Directory();
        homeDir.setOwnerId(ownerId);
        homeDir.setName(HOME_DIR);
        homeDir.setParentId(null);

        Instant now = Instant.now(Clock.systemUTC());
        homeDir.setCreatedAt(now);
        homeDir.setUpdatedAt(now);

        homeDir = directoryRepository.save(homeDir);
        return homeDir.getId();
    }

    @Override
    public List<DirectoryDto> getDirHierarchy(long ownerId) {
        List<Directory> dirs = directoryRepository.findByOwnerId(ownerId);
        return dirs.stream().map(DirectoryDto::from).toList();
    }

    @Override
    public DirectoryDto createDir(String name, ObjectId parentId, long ownerId) {
        Directory dir = new Directory();
        dir.setName(name);
        dir.setParentId(parentId);
        dir.setOwnerId(ownerId);
        Instant now = Instant.now(Clock.systemUTC());
        dir.setCreatedAt(now);
        dir.setUpdatedAt(now);
        dir = directoryRepository.save(dir);
        return DirectoryDto.from(dir);
    }


}
