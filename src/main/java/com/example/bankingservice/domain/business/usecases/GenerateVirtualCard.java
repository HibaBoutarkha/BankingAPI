package com.example.bankingservice.domain.business.usecases;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.utils.CustomException;

public interface GenerateVirtualCard {

    Account handle(GenerateVirtualCardRequest request) throws CustomException;
}
