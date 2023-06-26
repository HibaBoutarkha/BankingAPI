package com.example.bankingservice.domain.business.apis;

import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.domain.business.usecases.GetCustomerDetails;
import com.example.bankingservice.domain.business.usecases.GetCustomerDetailsRequest;
import com.example.bankingservice.domain.dtoRepos.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("banking-service/customers")
@AllArgsConstructor
public class CustomerAPI implements GetCustomerDetails {

    private CustomerRepo customerRepo;

    @Override
    @GetMapping("/customer-details")
    public ResponseEntity<Customer> handle(@ModelAttribute GetCustomerDetailsRequest request) {
        Customer customer = customerRepo.findByID(request.getUUID()).get();
        return ResponseEntity.status(HttpStatus.OK).body(customer);
    }
}
