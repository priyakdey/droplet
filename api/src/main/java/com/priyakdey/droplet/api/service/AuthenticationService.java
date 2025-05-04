package com.priyakdey.droplet.api.service;

/**
 * @author Priyak Dey
 */
public interface AuthenticationService {

    String generateToken(Long userId);

}
