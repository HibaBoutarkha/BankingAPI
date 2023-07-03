package com.example.bankingservice.domain.dtoRepos;

import com.example.bankingservice.domain.base.Account;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

public interface AccountRepo {

    List<Account> findAllByOwner(Long ownerID);

    Optional<Account> findByAccountNumber(String accountNumber);

    Account save(Account account) throws DataIntegrityViolationException;
}
