package com.priyakdey.droplet.api.service.impl;

import com.priyakdey.droplet.api.dto.AccountDto;
import com.priyakdey.droplet.api.entity.Account;
import com.priyakdey.droplet.api.exception.AccountDoesNotExistException;
import com.priyakdey.droplet.api.exception.InvalidCredentialsException;
import com.priyakdey.droplet.api.repository.AccountRepository;
import com.priyakdey.droplet.api.service.AuthenticationService;
import com.priyakdey.droplet.api.service.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Priyak Dey
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationServiceImpl(AccountRepository accountRepository,
                                     BCryptPasswordEncoder passwordEncoder,
                                     @Qualifier("jwtServiceHS256Impl") JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String generateToken(Long userId) {
        return jwtService.generateToken(userId);
    }

    @Override
    public AccountDto authenticate(String email, char[] password) {
        try {
            Account account = accountRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new AccountDoesNotExistException("No account registered with this email"));
            if (!passwordEncoder.matches(new String(password), account.getPasswordHash())) {
                throw new InvalidCredentialsException("Invalid password");
            }

            String token = generateToken(account.getId());
            return new AccountDto(account.getId(), account.getName(), token);
        } finally {
            Arrays.fill(password, '\0');
        }
    }

}
