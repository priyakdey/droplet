package com.priyakdey.droplet.security.jwt;

/**
 * @author Priyak Dey
 */
public interface TokenVerifier<T> {

    T verify(String token);

}
