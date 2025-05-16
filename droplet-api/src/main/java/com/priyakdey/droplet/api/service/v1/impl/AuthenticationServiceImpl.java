package com.priyakdey.droplet.api.service.v1.impl;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.entity.v1.Directory;
import com.priyakdey.droplet.api.entity.v1.Profile;
import com.priyakdey.droplet.api.exception.EmailExistsException;
import com.priyakdey.droplet.api.exception.InvalidCredentialsException;
import com.priyakdey.droplet.api.exception.ServerException;
import com.priyakdey.droplet.api.model.dto.v1.ProfileMetadataDto;
import com.priyakdey.droplet.api.model.dto.v1.SignupDto;
import com.priyakdey.droplet.api.model.response.v1.AuthResponse;
import com.priyakdey.droplet.api.security.core.SecureCharSequence;
import com.priyakdey.droplet.api.service.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author Priyak Dey
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final AccountService accountService;
    private final ProfileService profileService;
    private final InodeService inodeService;
    private final BlobService blobService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(AccountService accountService, ProfileService profileService,
                                     InodeService inodeService, BlobService blobService,
                                     TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.profileService = profileService;
        this.inodeService = inodeService;
        this.blobService = blobService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional(propagation = REQUIRED, isolation = READ_COMMITTED)
    public AuthResponse signup(SignupDto signupDto) {
        String email = signupDto.email();
        if (accountService.accountExists(email)) {
            throw new EmailExistsException("This email is already registered with us.");
        }

        boolean accountCreated = false;
        boolean profileCreated = false;
        boolean homeDirCreated = false;
        boolean blobContainerCreated = false;

        String name = signupDto.name();
        String password = signupDto.password();
        String timezone = signupDto.timezone();

        Account account = null;
        Profile profile = null;
        Directory homeDir = null;
        String containerName = null;

        try {
            account = accountService.create(email, password);
            accountCreated = true;

            profile = profileService.create(name, timezone, account);
            profileCreated = true;

            homeDir = inodeService.createHomeDir(account.getId());
            homeDirCreated = true;

            containerName = "home-" + account.getId();
            blobService.createContainer(containerName);
            blobContainerCreated = true;

            String token = tokenService.generate(account.getId(), name);

            ProfileMetadataDto profileMetadataDto = new ProfileMetadataDto(account.getId(),
                    profile.getId(), homeDir.getId().toHexString(), name, timezone);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setProfile(profileMetadataDto);
            authResponse.setToken(token);
            return authResponse;
        } catch (Exception e) {
            logger.error("Error creating the account: ", e);

            if (blobContainerCreated) blobService.deleteContainer(containerName);
            if (homeDirCreated) inodeService.deleteById(homeDir.getId());
            if (profileCreated) profileService.deleteById(profile.getId());
            if (accountCreated) accountService.deleteById(account.getId());

            throw new ServerException();
        }

    }

    @Override
    public AuthResponse authenticate(String email, SecureCharSequence rawPassword) {
        Optional<Account> optional = accountService.getByEmail(email);
        if (optional.isEmpty()) {
            throw new InvalidCredentialsException("This email is not registered with us.");
        }

        Account account = optional.get();
        if (!passwordEncoder.matches(rawPassword, account.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        Integer accountId = account.getId();

        Profile profile = profileService.getProfile(account).orElseThrow(() -> {
            logger.error("Could not find profile for account id: {}", accountId);
            return new ServerException();
        });

        Directory homeDir = inodeService.getHomeDir(accountId).orElseThrow(() -> {
            logger.error("Could not find home directory for account id: {}", accountId);
            return new ServerException();
        });

        Integer profileId = profile.getId();
        String name = profile.getName();
        String preferredTz = profile.getPreferredTz();

        String token = tokenService.generate(accountId, name);

        ProfileMetadataDto profileMetadataDto = new ProfileMetadataDto(accountId,
                profileId, homeDir.getId().toHexString(), name, preferredTz);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setProfile(profileMetadataDto);
        return authResponse;
    }

}
