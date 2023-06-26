package com.example.bankingservice.outbound.services;

import com.example.bankingservice.configurations.CustomModelMapper;
import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.base.PhysicalCard;
import com.example.bankingservice.domain.dtoRepos.AccountInfoRepo;
import com.example.bankingservice.domain.dtoRepos.PhysicalCardRepo;
import com.example.bankingservice.domain.utils.CustomException;
import com.example.bankingservice.outbound.jpa.accounts.AccountEntity;
import com.example.bankingservice.outbound.jpa.accounts.AccountEntityRepo;
import com.example.bankingservice.outbound.jpa.virtualcards.VirtualCardEntity;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountRepoImpl implements AccountInfoRepo, PhysicalCardRepo {

    private AccountEntityRepo accountEntityRepo;
    private CustomModelMapper mapper;

    @Override
    public List<Account> findAllByOwner(Long ownerID) {
        List<AccountEntity> entities = accountEntityRepo.findAllByOwner(ownerID);
        return mapper.mapList(entities, Account.class);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        Optional<AccountEntity> entity = accountEntityRepo.findByAccountNumber(accountNumber);
        if(entity.isPresent()) return Optional.ofNullable(mapper.map(entity.get(), Account.class));
        return Optional.empty();
    }

    @Override
    public Account save(Account account) throws DataIntegrityViolationException {
        AccountEntity entity = mapper.map(account, AccountEntity.class);
        for(VirtualCardEntity virtualCard: entity.getVirtualCardList()){
            virtualCard.setAccount(entity);
        }

        return mapper.map(accountEntityRepo.save(entity), Account.class);
    }

    @Override
    public Optional<PhysicalCard> findByCardNumber(String cardNumber) {
        PhysicalCard card = accountEntityRepo.findByCardNumber(cardNumber).get().getPhysicalCard();

        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setCardNumber(card.getCardNumber());
        physicalCard.setCvc(card.getCvc());
        physicalCard.setCreationDate(card.getCreationDate());
        physicalCard.setIsEnabled(card.getIsEnabled());
        physicalCard.setExpirationDate(card.getExpirationDate());
        return Optional.ofNullable(physicalCard);
    }

    @Override
    public PhysicalCard save(PhysicalCard card, Long UUID) throws CustomException {
        Optional<AccountEntity> entity = accountEntityRepo.findByOwnerAndPhysicalCard(card.getCardNumber(), UUID);
        if(entity.isPresent()){
            AccountEntity account = entity.get();
            account.setPhysicalCard(card);
            accountEntityRepo.save(account);
            return card;
        }
        else throw new CustomException(HttpStatus.BAD_REQUEST.value(), "account.not.found");
    }
}
