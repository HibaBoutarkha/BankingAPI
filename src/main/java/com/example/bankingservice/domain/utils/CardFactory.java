package com.example.bankingservice.domain.utils;

import com.example.bankingservice.domain.base.Card;
import com.example.bankingservice.domain.base.PhysicalCard;
import com.example.bankingservice.domain.base.VirtualCard;
import com.example.bankingservice.domain.dtoRepos.PhysicalCardRepo;
import com.example.bankingservice.domain.dtoRepos.VirtualCardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardFactory {

    private static PhysicalCardRepo physicalCardRepo;
    private static VirtualCardRepo virtualCardRepo;

    @Autowired
    public CardFactory(PhysicalCardRepo physicalCardRepo, VirtualCardRepo virtualCardRepo) {
        CardFactory.physicalCardRepo = physicalCardRepo;
        CardFactory.virtualCardRepo = virtualCardRepo;
    }

    public static Card getCard(String cardNumber, String type) throws CustomException {

        switch (type) {
            case "physical": {
                Optional<PhysicalCard> card = physicalCardRepo.findByCardNumber(cardNumber);
                return (Card) card.orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "card.not.found"));
            }
            case "virtual": {
                Optional<VirtualCard> card = virtualCardRepo.findByCardNumber(cardNumber);
                return card.orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST.value(), "card.not.found"));
            }
            default:
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), "card.type.not.found");
        }
    }

    public static Card saveCard(Card card, String type, Long UUID) throws CustomException {
        switch (type) {
            case "physical": {
                return physicalCardRepo.save((PhysicalCard) card, UUID);
            }
            case "virtual": {
                return virtualCardRepo.save((VirtualCard) card);
            }
            default:
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), "card.type.not.found");
        }
    }
}
