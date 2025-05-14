package com.priyakdey.droplet.api.service.v1;

import com.priyakdey.droplet.api.entity.v1.Account;

import java.util.Optional;

/**
* @author Priyak Dey
*/
public interface AccountService {

    Account create(String email, String password);

    void deleteById(Integer id);

    Optional<Account> getByEmail(String email);

    boolean accountExists(String email);
}
