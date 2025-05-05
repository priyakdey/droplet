package com.priyakdey.droplet.api.service;

import com.priyakdey.droplet.api.dto.AccountDto;

/**
 * @author Priyak Dey
 */
public interface AuthenticationService {

    String generateToken(Long userId);

    AccountDto authenticate(String email, char[] password);

}
