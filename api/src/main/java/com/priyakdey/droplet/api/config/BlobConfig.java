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
public class BlobConfig {

    @Bean
    public BlobServiceClient blobServiceClient(
            AzureStorageBlobProperties azureStorageBlobProperties) {
        String accountName = azureStorageBlobProperties.getAccountName();
        String accountKey = azureStorageBlobProperties.getAccountKey();
        AzureNamedKeyCredential credential = new AzureNamedKeyCredential(accountName, accountKey);
        return new BlobServiceClientBuilder()
                .credential(credential)
                .endpoint(azureStorageBlobProperties.getEndpoint())
                .serviceVersion(BlobServiceVersion.V2020_04_08)
                .buildClient();
    }

}
