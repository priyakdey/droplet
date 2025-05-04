package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.entity.Account;
import com.priyakdey.droplet.api.repository.AccountRepository;
import com.priyakdey.droplet.api.service.SignupService;
import org.springframework.stereotype.Service;

/**
 * @author Priyak Dey
 */
@Service
public class SignupServiceImpl implements SignupService {

    private final AccountRepository accountRepository;

    public SignupServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Long signup(String name, String email, String password) {
        Account account = new Account(name, email, password);
        account = accountRepository.save(account);
        return account.getId();
    }
}
