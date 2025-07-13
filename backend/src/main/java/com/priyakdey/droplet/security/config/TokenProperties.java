package com.priyakdey.droplet.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


/**
 * @author Priyak Dey
 */
@ConfigurationProperties(prefix = "token")
public class TokenProperties {

    private final State state = new State();
    private Token jwt;

    public State getState() {
        return state;
    }

    public Token getJwt() {
        return jwt;
    }

    public void setJwt(Token jwt) {
        this.jwt = jwt;
    }

    public static class State {
        private Map<String, Token> providers;

        public Map<String, Token> getProviders() {
            return providers;
        }

        public void setProviders(Map<String, Token> providers) {
            this.providers = providers;
        }
    }


    public record Token(byte[] secret, String issuer, int expirationInSec, int leewayInSec) {
    }

}
