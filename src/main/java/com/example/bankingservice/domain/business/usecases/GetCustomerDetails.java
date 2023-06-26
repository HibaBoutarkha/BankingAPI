package com.example.bankingservice.domain.business.usecases;

import com.example.bankingservice.domain.base.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

public interface GetCustomerDetails {
    public ResponseEntity<Customer> handle(@ModelAttribute GetCustomerDetailsRequest request);
}
