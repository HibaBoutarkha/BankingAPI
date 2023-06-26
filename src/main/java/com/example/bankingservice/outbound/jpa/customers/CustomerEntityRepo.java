package com.example.bankingservice.outbound.jpa.customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEntityRepo extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByUUID(Long UUID);
    Optional<CustomerEntity> findByPhoneNumber(String phoneNumber);
}
