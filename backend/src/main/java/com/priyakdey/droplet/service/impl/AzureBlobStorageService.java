package com.priyakdey.droplet.service.impl;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.PublicAccessType;
import com.azure.storage.blob.options.BlobContainerCreateOptions;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.priyakdey.droplet.exception.InternalServerException;
import com.priyakdey.droplet.service.BlobStorageService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

/**
 * @author Priyak Dey
 */
@Service
public class AzureBlobStorageService implements BlobStorageService {
    private static final Logger logger = LoggerFactory.getLogger(AzureBlobStorageService.class);

    private final BlobServiceClient blobServiceClient;

    public AzureBlobStorageService(BlobServiceClient blobServiceClient) {
        this.blobServiceClient = blobServiceClient;
    }

    @Override
    public String uploadAvatar(byte[] image, String blobName, Instant expiresAt) {
        BlobClient blobClient = blobServiceClient.getBlobContainerClient("avatars")
                .getBlobClient(blobName);
        blobClient.upload(new ByteArrayInputStream(image), image.length, true);

        BlobSasPermission permission = new BlobSasPermission().setReadPermission(true);
        OffsetDateTime expiryTime = expiresAt.atOffset(ZoneOffset.UTC);
        BlobServiceSasSignatureValues sasValues =
                new BlobServiceSasSignatureValues(expiryTime, permission)
                        .setStartTime(OffsetDateTime.now(ZoneOffset.UTC));

        String sasToken = blobClient.generateSas(sasValues);
        return blobClient.getBlobUrl() + "?" + sasToken;
    }

    @Override
    public boolean createContainer(String container, Map<String, String> metadata,
                                   PublicAccessType accessType, boolean ignoreIfExists) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(container);
        // TODO: use context to pass down the trace id. See if we can hack into the client
        // to provide a custom httpclient, which can also generate a new span
        BlobContainerCreateOptions blobContainerCreateOptions = new BlobContainerCreateOptions();
        blobContainerCreateOptions.setMetadata(metadata);
        blobContainerCreateOptions.setPublicAccessType(accessType);

        Response<Boolean> response = containerClient.createIfNotExistsWithResponse(
                blobContainerCreateOptions, null, Context.NONE);

        int statusCode = response.getStatusCode();
        if (statusCode != 201 && (statusCode != 409 && !ignoreIfExists)) {
            logger.error("Could not create container: {}, status code: {}", container, statusCode);
            return false;
        }

        return true;
    }

    @Override
    public void deleteContainer(String container) {
        // TODO: we do not handle error here
        blobServiceClient.getBlobContainerClient(container).delete();
    }

    @PostConstruct
    public void init() {
        Map<String, String> metadata = Map.of(
                "purpose", "profile-pictures",
                "access", "public",
                "description", "User avatar images"
        );

        logger.info("Creating `avatar` container if not exists.");
        boolean isCreated = createContainer("avatars", metadata,
                PublicAccessType.CONTAINER, true);
        if (!isCreated) {
            throw new InternalServerException();
        }
    }

}
