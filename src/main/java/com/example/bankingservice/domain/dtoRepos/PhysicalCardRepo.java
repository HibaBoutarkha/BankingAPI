package com.example.bankingservice.domain.dtoRepos;

import com.example.bankingservice.domain.base.PhysicalCard;
import com.example.bankingservice.domain.utils.CustomException;

import java.util.Optional;

public interface PhysicalCardRepo {

    Optional<PhysicalCard> findByCardNumber(String cardNumber);
    PhysicalCard save(PhysicalCard card, Long UUID) throws CustomException;
}
