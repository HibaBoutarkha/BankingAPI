package com.example.bankingservice.outbound.services;

import com.example.bankingservice.domain.base.VirtualCard;
import com.example.bankingservice.domain.dtoRepos.VirtualCardRepo;
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
    public VirtualCard save(VirtualCard card) {
        VirtualCardEntity entity = mapper.map(card, VirtualCardEntity.class);
        return mapper.map(virtualCardRepo.save(entity), VirtualCard.class);
    }

    @Override
    public Optional<VirtualCard> findByCardNumberAndOwner(String cardNumber, Long UUID) {
        Optional<VirtualCardEntity> entity = virtualCardRepo.findByCardNumberAndOwner(cardNumber, UUID);
        if(entity.isPresent()) Optional.of(mapper.map(entity.get(), VirtualCard.class));
        return Optional.empty();
    }
}
