package com.priyakdey.droplet.api.service;

import org.bson.types.ObjectId;

/**
 * @author Priyak Dey
 */
public interface DirectoryService {

    ObjectId createHomeDir(long ownerId);

}
