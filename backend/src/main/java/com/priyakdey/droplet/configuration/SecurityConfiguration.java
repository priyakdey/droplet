package com.priyakdey.droplet.configuration;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Priyak Dey
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(CorsEndpointProperties corsEndpointProperties) {
        String[] allowedOrigins = corsEndpointProperties.getAllowedOrigins().toArray(String[]::new);
        String[] allowedMethods = corsEndpointProperties.getAllowedMethods().toArray(String[]::new);
        boolean allowCredentials = corsEndpointProperties.getAllowCredentials();
        String[] allowedHeaders = corsEndpointProperties.getAllowedHeaders().toArray(String[]::new);

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(allowCredentials);
            }
        };
    }

}
