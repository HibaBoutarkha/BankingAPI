package com.example.bankingservice.domain.utils;

import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.domain.base.User;
import com.example.bankingservice.domain.dtoRepos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFactory {

    private static CustomerRepo customerRepo;

    @Autowired
    public UserFactory(CustomerRepo customerRepo) {
        UserFactory.customerRepo = customerRepo;
    }

    public static User getUser(String phoneNumber, String role) throws Exception {

        switch (role) {
            case "customer": {
                Optional<Customer> customer = customerRepo.findByPhoneNumber(phoneNumber);
                return customer.orElseThrow(() -> new Exception("customer.not.found"));
            }
            default:
                return null;
        }
    }

}
