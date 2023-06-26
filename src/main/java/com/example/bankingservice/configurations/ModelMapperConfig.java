package com.example.bankingservice.configurations;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.base.Address;
import com.example.bankingservice.domain.base.Customer;
import com.example.bankingservice.outbound.jpa.accounts.AccountEntity;
import com.example.bankingservice.outbound.jpa.customers.CustomerEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper createMapper() {
        CustomModelMapper mapper = new CustomModelMapper();

        addressToSqlMapping(mapper);
        sqlToAddressMapping(mapper);
        return mapper;
    }


    public void addressToSqlMapping(ModelMapper mapper) {

        Converter<Address, String> addressToSql =
                ctx -> ctx.getSource() == null ? null : ctx.getSource().toSql();

        mapper.createTypeMap(Customer.class, CustomerEntity.class)
                .addMappings(mp -> {
                    mp.using(addressToSql).map(source -> source.getBirthAddress(), CustomerEntity::setBirthAddress);
                });

        mapper.createTypeMap(Account.class, AccountEntity.class)
                .addMappings(mp -> {
                    mp.using(addressToSql).map(source -> source.getAgencyAddress(), AccountEntity::setAgencyAddress);
                });
    }

    public void sqlToAddressMapping(ModelMapper mapper) {
        Converter<String, Address> sqlToAddress =
                ctx -> ctx.getSource() == null ? null : Address.toAddress(ctx.getSource());

        mapper.createTypeMap(CustomerEntity.class, Customer.class)
                .addMappings(mp -> {
                    mp.using(sqlToAddress).map(source -> source.getBirthAddress(), Customer::setBirthAddress);
                });

        mapper.createTypeMap(AccountEntity.class, Account.class)
                .addMappings(mp -> {
                    mp.using(sqlToAddress).map(source -> source.getAgencyAddress(), Account::setAgencyAddress);
                });

    }

}