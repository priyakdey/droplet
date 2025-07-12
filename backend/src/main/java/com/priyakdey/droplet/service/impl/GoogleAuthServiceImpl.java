package com.priyakdey.droplet.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.priyakdey.droplet.model.dto.NewProfileDto;
import com.priyakdey.droplet.service.AuthService;
import com.priyakdey.droplet.service.ProfileService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class GoogleAuthServiceImpl implements AuthService {
    private static final String PROVIDER_NAME = "google";
    private static final String PROVIDER_ID_DMT = "google|%s";

    private final ProfileService profileService;

    public GoogleAuthServiceImpl(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public ObjectId login(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);

            String sub = decodedJWT.getSubject();
            String providerId = String.format(PROVIDER_ID_DMT, sub);

            if (profileService.existsByProviderId(providerId)) {
                return profileService.getByProviderId(providerId);
            }

            String name = decodedJWT.getClaim("name").asString();
            String email = decodedJWT.getClaim("email").asString();
            String profilePicUrl = decodedJWT.getClaim("picture").asString();
            NewProfileDto dto = new NewProfileDto(name, email, profilePicUrl, PROVIDER_NAME,
                    providerId);
            return profileService.save(dto);

        } catch (JWTDecodeException e) {
            throw new RuntimeException(e); // TODO: custom exception
        }

    }
}
