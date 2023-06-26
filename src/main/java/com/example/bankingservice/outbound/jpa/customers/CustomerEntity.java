package com.example.bankingservice.outbound.jpa.customers;

import com.example.bankingservice.outbound.jpa.accounts.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor


public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid")
    private Long UUID;

    @Column(unique = true)
    private String phoneNumber;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthdate;
    private String birthAddress;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<AccountEntity> accounts;
}
