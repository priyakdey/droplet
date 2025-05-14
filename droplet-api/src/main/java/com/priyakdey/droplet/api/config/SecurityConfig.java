package com.priyakdey.droplet.api.config;

import com.priyakdey.droplet.api.security.hasher.SecureBCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.security.SecureRandom;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2B;

/**
 * @author Priyak Dey
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest -> authorizeRequest
                        .requestMatchers(POST, "/v1/signup").permitAll()
                        .requestMatchers(POST, "/v1/login").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecureBCryptPasswordEncoder passwordEncoder() {
        // TODO: generate a random byte seed
        SecureRandom secureRandom = new SecureRandom();
        return new SecureBCryptPasswordEncoder(10, $2B, secureRandom);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}
