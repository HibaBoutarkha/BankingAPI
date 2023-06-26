package com.example.bankingservice.domain.business.apis;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.business.usecases.GetAccount;
import com.example.bankingservice.domain.business.usecases.GetAccountInfoRequest;
import com.example.bankingservice.domain.dtoRepos.AccountInfoRepo;
import com.example.bankingservice.domain.utils.CustomException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("banking-service/accounts")
@AllArgsConstructor
public class AccountAPI implements GetAccount {

    private AccountInfoRepo accountInfoRepo;

    @Override
    @GetMapping("/accounts-info")
    public ResponseEntity<List<Account>> handle(@ModelAttribute GetAccountInfoRequest request) throws CustomException {
        List<Account> accounts = accountInfoRepo.findAllByOwner(request.getUUID());
        if (accounts.isEmpty()) throw new CustomException(HttpStatus.BAD_REQUEST.value(), "no.accounts.found");
        return ResponseEntity.status(HttpStatus.OK)
                .body(accounts);
    }
}
