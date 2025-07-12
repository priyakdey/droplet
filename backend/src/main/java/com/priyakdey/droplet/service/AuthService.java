package com.priyakdey.droplet.service;

import org.bson.types.ObjectId;

/**
 * @author Priyak Dey
 */
public interface AuthService<T> {

    ObjectId login(T t);

}
