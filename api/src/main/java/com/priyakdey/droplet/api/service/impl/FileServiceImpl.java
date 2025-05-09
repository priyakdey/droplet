package com.priyakdey.droplet.api.service.impl;

import com.azure.core.http.rest.Response;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.options.BlobContainerCreateOptions;
import com.priyakdey.droplet.api.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private final BlobServiceClient blobServiceClient;

    public FileServiceImpl(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }


    @Override
    public void createContainer(String containerName) {
        BlobContainerCreateOptions createOptions = new BlobContainerCreateOptions();
        createOptions.setPublicAccessType(PublicAccessType.CONTAINER);
        Response<BlobContainerClient> response = blobServiceClient
                .createBlobContainerIfNotExistsWithResponse(containerName, createOptions, null);
        int code = response.getStatusCode();
        if (code != 201) {
            logger.error("Error creating container {}: {}", containerName, response.getStatusCode());
            throw new RuntimeException("Error creating container: " + response.getStatusCode());
        }
    }
    
}
