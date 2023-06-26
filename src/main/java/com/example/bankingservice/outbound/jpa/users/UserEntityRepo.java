package com.example.bankingservice.outbound.jpa.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepo extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByPhoneNumber(String phoneNumber);
}
