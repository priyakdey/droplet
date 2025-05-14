package com.priyakdey.droplet.api.service.v1;

/**
 * @author Priyak Dey
 */
public interface BlobService {

    void createContainer(String containerName);

    void deleteContainer(String containerName);

}
