package com.example.bankingservice.domain.dtoRepos;

import com.example.bankingservice.domain.base.VirtualCard;

import java.util.Optional;

public interface VirtualCardRepo {

    Optional<VirtualCard> findByCardNumber(String cardNumber);

    VirtualCard save(VirtualCard card);

    Optional<VirtualCard> findByCardNumberAndOwner(String cardNumber, Long UUID);
}
