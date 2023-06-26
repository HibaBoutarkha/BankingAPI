package com.example.bankingservice.domain.dtoRepos;


import com.example.bankingservice.domain.base.Customer;

import java.util.Optional;

public interface CustomerRepo {

    Customer save(Customer customer);
    Optional<Customer> findByID(Long UUID);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
