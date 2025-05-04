package com.priyakdey.droplet.api.service;

/**
 * @author Priyak Dey
 */
public interface JwtService {

    String generateToken(Long userId);

}
