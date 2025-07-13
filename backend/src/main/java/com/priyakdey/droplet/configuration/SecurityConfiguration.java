package com.priyakdey.droplet.configuration;

import com.priyakdey.droplet.security.config.AppCorsProperties;
import com.priyakdey.droplet.security.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
    public SecurityFilterChain configure(
            HttpSecurity http, JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(AppCorsProperties appCorsProperties) {
        String[] allowedOrigins = appCorsProperties.getAllowedOrigins().toArray(String[]::new);
        String[] allowedMethods = appCorsProperties.getAllowedMethods().toArray(String[]::new);
        String[] allowedHeaders = appCorsProperties.getAllowedHeaders().toArray(String[]::new);
        boolean allowCredentials = appCorsProperties.isAllowCredentials();

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods(allowedMethods)
                        .allowedHeaders(allowedHeaders)
                        .allowCredentials(allowCredentials);
            }
        };
    }

}
