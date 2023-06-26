package com.example.bankingservice.outbound.services;

import com.example.bankingservice.domain.dtoRepos.CustomerRepo;
import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.outbound.jpa.customers.CustomerEntity;
import com.example.bankingservice.outbound.jpa.customers.CustomerEntityRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerRepoImpl implements CustomerRepo {

    private CustomerEntityRepo customerEntityRepo;
    private ModelMapper mapper;

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.map(customer, CustomerEntity.class);
        return mapper.map(customerEntityRepo.save(entity), Customer.class);
    }

    @Override
    public Optional<Customer> findByID(Long UUID) throws UsernameNotFoundException{
        Optional<CustomerEntity> entity = customerEntityRepo.findByUUID(UUID);
        return Optional.ofNullable(mapper.map(entity, Customer.class));
    }

    @Override
    public Optional<Customer> findByPhoneNumber(String phoneNumber) {
        Optional<CustomerEntity> entity = customerEntityRepo.findByPhoneNumber(phoneNumber);
        return Optional.ofNullable(mapper.map(entity, Customer.class));
    }
}
