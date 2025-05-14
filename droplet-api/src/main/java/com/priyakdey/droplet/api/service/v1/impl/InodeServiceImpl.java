package com.priyakdey.droplet.api.service.v1.impl;

import com.priyakdey.droplet.api.entity.v1.Directory;
import com.priyakdey.droplet.api.exception.ServerException;
import com.priyakdey.droplet.api.repository.v1.DirectoryRepository;
import com.priyakdey.droplet.api.repository.v1.FileRepository;
import com.priyakdey.droplet.api.service.v1.InodeService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Priyak Dey
 */
@Service
public class InodeServiceImpl implements InodeService {
    private static final Logger logger = LoggerFactory.getLogger(InodeServiceImpl.class);

    private final DirectoryRepository directoryRepository;
    private final FileRepository fileRepository;

    public InodeServiceImpl(DirectoryRepository directoryRepository,
                            FileRepository fileRepository) {
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public Directory createHomeDir(Integer ownerId) {
        Directory directory = new Directory("home", ownerId);
        return directoryRepository.save(directory);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        if (!ObjectId.isValid(id)) {
            logger.warn("Received invalid hex id: {}", id);
            throw new ServerException("Something went wrong. Try again later or contact us.");
        }

        deleteById(new ObjectId(id));
    }

    @Override
    @Transactional
    public void deleteById(ObjectId id) {
        directoryRepository.deleteById(id);
    }
}
