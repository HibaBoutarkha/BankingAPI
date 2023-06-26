package com.example.bankingservice.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private String accountNumber;
    private String rib;
    private Boolean isActive;
    private Address agencyAddress;
    private PhysicalCard physicalCard;
    private List<VirtualCard> virtualCardList;
    private Customer owner;
}
