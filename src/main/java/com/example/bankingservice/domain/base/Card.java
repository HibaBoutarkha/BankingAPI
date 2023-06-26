package com.example.bankingservice.domain.base;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Card {


    private String cardNumber;
    private Date expirationDate;
    private Date creationDate;
    private Boolean isEnabled;
    private String cvc;

    public void updateStatus() {
        this.setIsEnabled(!this.getIsEnabled());
    }

    ;
}
