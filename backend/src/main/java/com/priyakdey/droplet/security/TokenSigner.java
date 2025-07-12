package com.priyakdey.droplet.security;

/**
 * @author Priyak Dey
 */
public interface TokenSigner<T> {
    String sign(T payload);
}
