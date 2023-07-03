package com.example.bankingservice.domain.dtoRepos;

import com.example.bankingservice.domain.base.VirtualCard;
import com.example.bankingservice.domain.utils.CustomException;

import java.util.Optional;

public interface VirtualCardRepo {

    Optional<VirtualCard> findByCardNumber(String cardNumber);

    VirtualCard save(VirtualCard card) throws CustomException;

    Optional<VirtualCard> findByCardNumberAndOwner(String cardNumber, Long UUID);
}
