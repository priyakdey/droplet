package com.priyakdey.droplet.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * @author Priyak Dey
 */
@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

}
