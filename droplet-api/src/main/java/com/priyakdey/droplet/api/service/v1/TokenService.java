package com.priyakdey.droplet.api.service.v1;

import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author Priyak Dey
 */
public interface TokenService {

    String generate(Integer id, String name);

    DecodedJWT verify(String token);

    Integer getSubject(DecodedJWT jwt);

}
