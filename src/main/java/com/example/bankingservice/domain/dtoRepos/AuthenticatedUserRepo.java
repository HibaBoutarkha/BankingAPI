package com.example.bankingservice.domain.dtoRepos;

import com.example.bankingservice.domain.base.AuthenticatedUser;

import java.util.Optional;

public interface AuthenticatedUserRepo {

    AuthenticatedUser save(AuthenticatedUser authenticatedUser);

    Optional<AuthenticatedUser> findByPhoneNumber(String phoneNumber);
}
