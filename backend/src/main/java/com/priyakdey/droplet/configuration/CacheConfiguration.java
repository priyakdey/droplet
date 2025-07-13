package com.priyakdey.droplet.configuration;


import com.priyakdey.droplet.cache.Cache;
import com.priyakdey.droplet.security.payload.StatePayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Priyak Dey
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public Cache<String, StatePayload> stateCache(@Value("${token.state.ttl}") long ttl) {
        return new Cache<>(ttl, TimeUnit.MINUTES);
    }

}
