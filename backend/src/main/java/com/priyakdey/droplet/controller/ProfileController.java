package com.priyakdey.droplet.controller;

import com.priyakdey.droplet.model.dto.ProfileDto;
import com.priyakdey.droplet.model.response.ProfileDetailsResponse;
import com.priyakdey.droplet.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Priyak Dey
 */
@RestController
@RequestMapping(path = "/me", produces = APPLICATION_JSON_VALUE,
        consumes = APPLICATION_JSON_VALUE)
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileDetailsResponse> profileDetails(Principal principal) {
        String profileId = principal.getName();
        ProfileDto profileDto = profileService.getByProfileId(profileId);
        ProfileDetailsResponse profileDetailsResponse = new ProfileDetailsResponse(profileDto);
        return ResponseEntity.ok(profileDetailsResponse);
    }
}
