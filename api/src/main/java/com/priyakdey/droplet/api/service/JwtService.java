package com.priyakdey.droplet.api.service;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author Priyak Dey
 */
public interface JwtService {

    String generateToken(Long userId);

    DecodedJWT parseAndValidateToken(String token);

    long getUserId(DecodedJWT jwt);

}
