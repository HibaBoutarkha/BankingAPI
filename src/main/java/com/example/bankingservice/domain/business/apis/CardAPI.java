package com.example.bankingservice.domain.business.apis;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.base.Card;
import com.example.bankingservice.domain.base.VirtualCard;
import com.example.bankingservice.domain.business.usecases.GenerateVirtualCard;
import com.example.bankingservice.domain.business.usecases.GenerateVirtualCardRequest;
import com.example.bankingservice.domain.business.usecases.UpdateCardStatus;
import com.example.bankingservice.domain.business.usecases.UpdateCardStatusRequest;
import com.example.bankingservice.domain.dtoRepos.AccountRepo;
import com.example.bankingservice.domain.utils.CardFactory;
import com.example.bankingservice.domain.utils.CustomException;
import com.example.bankingservice.domain.utils.CardGenerator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banking-service/cards")
@AllArgsConstructor
public class CardAPI implements UpdateCardStatus, GenerateVirtualCard {

    private AccountRepo accountInfoRepo;

    @Override
    @PostMapping("/update-status")
    public Card handle(@RequestBody UpdateCardStatusRequest request) throws CustomException {
        Card card = CardFactory.getCard(request.getCardNumber(), request.getCardType());
        card.updateStatus();
        return CardFactory.saveCard(card, request.getCardType(), request.getUuid());
    }

    @Override
    @PostMapping("generate-virtual-card")
    public Account handle(@RequestBody GenerateVirtualCardRequest request) throws CustomException {
        Account account = accountInfoRepo.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "account.not.found"));

        int tries = 0;
        while (tries < 3) {
            tries++;
            VirtualCard virtualCard = (VirtualCard) CardGenerator.generateCard("virtual");
            List<VirtualCard> virtualCardList = account.getVirtualCardList();
            virtualCardList.add(virtualCard);
            try {

                Account accountInfo = accountInfoRepo.save(account);
                return accountInfo;

            } catch (DataIntegrityViolationException e) {
                System.out.println("cardNumber.already.exists");

            }
            return null;
        }
        return null;
    }
}
