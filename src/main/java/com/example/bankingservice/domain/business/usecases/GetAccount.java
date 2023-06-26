package com.example.bankingservice.domain.business.usecases;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.utils.CustomException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GetAccount {

    public ResponseEntity<List<Account>> handle(GetAccountInfoRequest request) throws CustomException;
}
