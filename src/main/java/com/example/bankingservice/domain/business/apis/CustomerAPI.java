package com.example.bankingservice.domain.business.apis;

import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.domain.business.usecases.GetCustomerDetails;
import com.example.bankingservice.domain.business.usecases.GetCustomerDetailsRequest;
import com.example.bankingservice.domain.dtoRepos.CustomerRepo;
import com.example.bankingservice.domain.utils.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("banking-service/customers")
@AllArgsConstructor
public class CustomerAPI implements GetCustomerDetails {

    private CustomerRepo customerRepo;

    @Override
    @GetMapping("/customer-details")
    public ResponseEntity<Customer> handle(@ModelAttribute GetCustomerDetailsRequest request) throws CustomException {
        Optional<Customer> customer = customerRepo.findByID(request.getUUID());
        if(customer.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(customer.get());
        throw new CustomException(400, "customer.not.found");
    }
}
