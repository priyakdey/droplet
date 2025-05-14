package com.priyakdey.droplet.api.service.v1.impl;

import com.azure.core.http.rest.Response;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.priyakdey.droplet.api.exception.ServerException;
import com.priyakdey.droplet.api.service.v1.BlobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class BlobServiceImpl implements BlobService {
    private static final Logger logger = LoggerFactory.getLogger(BlobServiceImpl.class);

    private final BlobServiceClient serviceClient;
    private final BlobServiceClient blobServiceClient;

    public BlobServiceImpl(BlobServiceClient serviceClient, BlobServiceClient blobServiceClient) {
        this.serviceClient = serviceClient;
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public void createContainer(String containerName) {
        Response<BlobContainerClient> response = serviceClient
                .createBlobContainerWithResponse(containerName, null, null, null);

        int statusCode = response.getStatusCode();
        if (statusCode != 201) {
            logger.error("Error creating blob container. Got response code: {}", statusCode);
            throw new ServerException("Something went wrong. Try again later or contact us.");
        }

    }

    @Override
    public void deleteContainer(String containerName) {
        // TODO: handle error case
        blobServiceClient.deleteBlobContainer(containerName);
    }
}
