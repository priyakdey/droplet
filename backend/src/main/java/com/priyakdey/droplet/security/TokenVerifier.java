package com.priyakdey.droplet.security;

/**
 * @author Priyak Dey
 */
public interface TokenVerifier<T> {

    T verify(String token);

}
