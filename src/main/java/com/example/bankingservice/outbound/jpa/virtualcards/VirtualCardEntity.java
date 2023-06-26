package com.example.bankingservice.outbound.jpa.virtualcards;

import com.example.bankingservice.outbound.jpa.accounts.AccountEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "virtual_cards")
@Data
public class VirtualCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountID")
    private AccountEntity account;


    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "card_expirationDate")
    private Date expirationDate;

    @Column(name = "card_creationDate")
    private Date creationDate;

    @Column(name = "card_isEnabled")
    private Boolean isEnabled;

    @Column(name = "card_cvc", unique = true)
    private String cvc;
}
