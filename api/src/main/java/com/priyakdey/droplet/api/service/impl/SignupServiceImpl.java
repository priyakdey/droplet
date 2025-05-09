package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.entity.Account;
import com.priyakdey.droplet.api.exception.EmailExistsException;
import com.priyakdey.droplet.api.repository.AccountRepository;
import com.priyakdey.droplet.api.service.DirectoryService;
import com.priyakdey.droplet.api.service.FileService;
import com.priyakdey.droplet.api.service.SignupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class SignupServiceImpl implements SignupService {

    private final AccountRepository accountRepository;
    private final DirectoryService directoryService;
    private final FileService fileService;

    private final Long maxStorageInBytes;

    public SignupServiceImpl(AccountRepository accountRepository, DirectoryService directoryService,
                             FileService fileService,
                             @Value("${droplet.max.allowed.storage}") Long maxStorageInBytes) {
        this.accountRepository = accountRepository;
        this.directoryService = directoryService;
        this.fileService = fileService;
        this.maxStorageInBytes = maxStorageInBytes;
    }

    @Override
    public Long signup(String name, String email, String password) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailExistsException("An account with same email already exists");
        }
        Account account = new Account(name, email, password, maxStorageInBytes);
        account = accountRepository.save(account);
        long id = account.getId();

        // TODO: Do we need to send back the home directory id?
        directoryService.createHomeDir(id);

        String containerName = "home-" + id;
        fileService.createContainer(containerName);

        return id;
    }
}
