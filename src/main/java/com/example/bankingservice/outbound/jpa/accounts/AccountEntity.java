package com.example.bankingservice.outbound.jpa.accounts;

import com.example.bankingservice.domain.base.PhysicalCard;
import com.example.bankingservice.outbound.jpa.customers.CustomerEntity;
import com.example.bankingservice.outbound.jpa.virtualcards.VirtualCardEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity {


    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    @Id
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(unique = true)
    private String rib;

    private Boolean isActive;


    @Column(name = "agency_address")
    private String agencyAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "cardNumber", column = @Column(name = "card_number", unique = true)),
            @AttributeOverride(name = "expirationDate", column = @Column(name = "card_expirationDate")),
            @AttributeOverride(name = "creationDate", column = @Column(name = "card_creationDate")),
            @AttributeOverride(name = "isEnabled", column = @Column(name = "card_isEnabled")),
            @AttributeOverride(name = "cvc", column = @Column(name = "card_cvc", unique = true))
    })
    private PhysicalCard physicalCard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerID")
    private CustomerEntity owner;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<VirtualCardEntity> virtualCardList;


}
