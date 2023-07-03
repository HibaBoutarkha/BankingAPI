package com.example.bankingservice.outbound.services;

import com.example.bankingservice.domain.base.VirtualCard;
import com.example.bankingservice.domain.dtoRepos.VirtualCardRepo;
import com.example.bankingservice.domain.utils.CustomException;
import com.example.bankingservice.outbound.jpa.virtualcards.VirtualCardEntity;
import com.example.bankingservice.outbound.jpa.virtualcards.VirtualCardEntityRepo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VirtualCardRepoImpl implements VirtualCardRepo {

    private VirtualCardEntityRepo virtualCardRepo;
    private ModelMapper mapper;
    @Override
    public Optional<VirtualCard> findByCardNumber(String cardNumber) {
        Optional<VirtualCardEntity> entity = virtualCardRepo.findByCardNumber(cardNumber);
        if(entity.isPresent()) return Optional.ofNullable(mapper.map(entity.get(), VirtualCard.class));
        return Optional.empty();
    }

    @Override
    public VirtualCard save(VirtualCard card) throws CustomException {
        Optional<VirtualCardEntity> entity = virtualCardRepo.findByCardNumber(card.getCardNumber());
        if (entity.isPresent()) {
            entity.get().setIsEnabled(card.getIsEnabled());
            return mapper.map(virtualCardRepo.save(entity.get()), VirtualCard.class);
        }
        throw new CustomException(400, "card.not.found");
    }


    @Override
    public Optional<VirtualCard> findByCardNumberAndOwner(String cardNumber, Long UUID) {
        Optional<VirtualCardEntity> entity = virtualCardRepo.findByCardNumberAndOwner(cardNumber, UUID);
        if(entity.isPresent()) Optional.of(mapper.map(entity.get(), VirtualCard.class));
        return Optional.empty();
    }
}
