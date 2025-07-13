package com.priyakdey.droplet.service;

import com.azure.storage.blob.models.PublicAccessType;

import java.time.Instant;
import java.util.Map;

/**
 * @author Priyak Dey
 */
public interface BlobStorageService {

    String uploadAvatar(byte[] image, String filename, Instant expiresAt);

    boolean createContainer(String container, Map<String, String> metadata,
                            PublicAccessType accessType, boolean ignoreIfExists);

    void deleteContainer(String container);
}
