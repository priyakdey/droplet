package com.priyakdey.droplet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * @author Priyak Dey
 */
public interface LoginController {

    ResponseEntity<Void> redirectToProviderLogin();

    ResponseEntity<Void> handleCallback(String code, String state);

    default URI buildUri(String uri, Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            builder.queryParam(key, val);
        }

        return builder.build().toUri();
    }

}
