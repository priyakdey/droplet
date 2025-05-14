package com.priyakdey.droplet.api.service.v1.impl;

import com.priyakdey.droplet.api.entity.v1.Account;
import com.priyakdey.droplet.api.repository.v1.AccountRepository;
import com.priyakdey.droplet.api.service.v1.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Priyak Dey
 */
@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account create(String email, String password) {
        Account account = new Account(email, password);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> getByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public boolean accountExists(String email) {
        return accountRepository.existsByEmail(email);
    }

}
