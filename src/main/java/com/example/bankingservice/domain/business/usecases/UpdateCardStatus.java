package com.example.bankingservice.domain.business.usecases;

import com.example.bankingservice.domain.base.Card;
import com.example.bankingservice.domain.utils.CustomException;

public interface UpdateCardStatus {

    public Card handle(UpdateCardStatusRequest request) throws CustomException;
}
