package com.priyakdey.droplet.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Priyak Dey
 */
public class GithubOAuthResponse {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "scope")
    private String scope;

    @JsonProperty(value = "token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
