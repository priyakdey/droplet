package com.priyakdey.droplet.security.jwt;

/**
 * @author Priyak Dey
 */
public class TokenService<T> {

    private final TokenSigner<T> signer;
    private final TokenVerifier<T> verifier;


    public TokenService(TokenSigner<T> signer, TokenVerifier<T> verifier) {
        this.signer = signer;
        this.verifier = verifier;
    }

    public String generate(T payload) {
        return signer.sign(payload);
    }

    public T decode(String token) {
        return verifier.verify(token);
    }

}
