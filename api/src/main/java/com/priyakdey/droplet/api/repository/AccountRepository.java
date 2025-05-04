package com.priyakdey.droplet.api.repository;

import com.priyakdey.droplet.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Priyak Dey
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
