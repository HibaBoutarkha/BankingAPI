package com.example.bankingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages ="com.example.bankingservice.outbound.jpa")
        public class BankingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingServiceApplication.class, args);
    }

}
