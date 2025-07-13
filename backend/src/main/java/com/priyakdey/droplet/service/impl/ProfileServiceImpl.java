package com.priyakdey.droplet.service.impl;

import com.priyakdey.droplet.entity.Profile;
import com.priyakdey.droplet.exception.InternalServerException;
import com.priyakdey.droplet.exception.ProfileNotFoundException;
import com.priyakdey.droplet.model.dto.NewProfileDto;
import com.priyakdey.droplet.repository.ProfileRepository;
import com.priyakdey.droplet.service.BlobStorageService;
import com.priyakdey.droplet.service.ProfileService;
import com.priyakdey.droplet.utils.MimeTypeUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * @author Priyak Dey
 */
@Service
public class ProfileServiceImpl implements ProfileService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;
    private final BlobStorageService blobStorageService;
    private final RestClient restClient;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              BlobStorageService blobStorageService,
                              RestClient restClient) {
        this.profileRepository = profileRepository;
        this.blobStorageService = blobStorageService;
        this.restClient = restClient;
    }

    @Override
    public ObjectId save(NewProfileDto newProfileDto) {
        Profile profile = newProfileDto.toProfile();
        ObjectId profileId = new ObjectId();
        profile.setId(profileId);

        // save the profile image in local store
        ResponseEntity<byte[]> imageResponse = restClient.get()
                .uri(newProfileDto.avatarUrl())
                .retrieve()
                .toEntity(byte[].class);

        HttpHeaders headers = imageResponse.getHeaders();
        String contentType = Objects.requireNonNull(headers.getContentType()).toString();
        String ext = MimeTypeUtils.getExtensionFromContentType(contentType);
        String blobName = profileId + ext;

        String cacheControl = headers.getCacheControl();
        Instant expiresAt = extractExpiryFromCacheControl(cacheControl);
        profile.setExpiresAt(expiresAt);

        byte[] image = imageResponse.getBody();
        if (image == null) {
            logger.error("Could not retrieve the profile picture");
            // TODO: do not short-circuit, do a fallback
            throw new InternalServerException();
        }

        String avatarUrl = blobStorageService.uploadAvatar(image, blobName, expiresAt);
        profile.setAvatarUrl(avatarUrl);

        // create user specific container
        String container = profileId.toString();
        Map<String, String> metadata = Map.of(
                "user", profileId.toString(),
                "description", "Container for the user files",
                "access", "private",
                "created_at", LocalDateTime.now(Clock.systemUTC()).toString()
        );
        boolean created = blobStorageService.createContainer(container, metadata,
                null, false);
        if (!created) {
            throw new InternalServerException();
        }

        try {
            profile.setContainer(container);
            profileRepository.save(profile);
            return profileId;
        } catch (Exception e) {
            logger.error("Error while trying to save the profile: {}. Trying to delete the {}",
                    e.getMessage(), container, e);
            blobStorageService.deleteContainer(container);
            throw new InternalServerException();
        }
    }

    @Override
    public boolean existsByProviderId(String providerId) {
        return profileRepository.existsByProviderId(providerId);
    }

    @Override
    public ObjectId getByProviderId(String providerId) {
        return profileRepository
                .findByProviderId(providerId)
                .orElseThrow(() -> {
                    logger.error("Profile not found by provider id {}", providerId);
                    return new ProfileNotFoundException();
                })
                .getId();
    }

    private Instant extractExpiryFromCacheControl(String cacheControl) {
        long maxAge = 86400;

        if (cacheControl != null && !cacheControl.isEmpty()) {
            try {
                // expected: Cache-Control: "public, max-age=86400, no-transform"
                String maxAgeString = cacheControl.split(",")[1].trim().split("=")[1];
                maxAge = Long.parseLong(maxAgeString);
            } catch (IndexOutOfBoundsException | NumberFormatException e) {
                logger.warn("Invalid Cache-Control. Expected `public, max-age=86400, no-transform`, but got {}",
                        cacheControl);
            }
        }

        return Instant.now().plusSeconds(maxAge);
    }

}
