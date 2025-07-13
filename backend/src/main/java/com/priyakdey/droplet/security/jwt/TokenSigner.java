package com.priyakdey.droplet.security.jwt;

/**
 * @author Priyak Dey
 */
public interface TokenSigner<T> {
    String sign(T payload);
}
