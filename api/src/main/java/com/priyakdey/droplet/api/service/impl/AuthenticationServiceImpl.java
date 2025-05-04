package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.service.AuthenticationService;
import com.priyakdey.droplet.api.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;

    public AuthenticationServiceImpl(@Qualifier("jwtServiceHS256Impl") JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(Long userId) {
        return jwtService.generateToken(userId);
    }
}
