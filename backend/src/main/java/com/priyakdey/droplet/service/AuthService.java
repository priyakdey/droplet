package com.priyakdey.droplet.service;

import org.bson.types.ObjectId;

/**
 * @author Priyak Dey
 */
public interface AuthService {

    ObjectId login(String token);

}
