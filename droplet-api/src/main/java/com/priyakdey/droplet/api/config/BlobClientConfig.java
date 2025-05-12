package com.priyakdey.droplet.api.config;

import com.azure.core.credential.AzureNamedKeyCredential;
import com.azure.spring.cloud.autoconfigure.implementation.storage.blob.properties.AzureStorageBlobProperties;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.BlobServiceVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Priyak Dey
 */
@Configuration
public class BlobClientConfig {

    @Bean
    public BlobServiceClient blobServiceClient(AzureStorageBlobProperties properties) {
        String accountName = properties.getAccountName();
        String accountKey = properties.getAccountKey();
        String endpoint = properties.getEndpoint();
        AzureNamedKeyCredential credential = new AzureNamedKeyCredential(accountName, accountKey);
        return new BlobServiceClientBuilder()
                .serviceVersion(BlobServiceVersion.V2020_04_08)
                .credential(credential)
                .endpoint(endpoint)
                .buildClient();
    }

}
