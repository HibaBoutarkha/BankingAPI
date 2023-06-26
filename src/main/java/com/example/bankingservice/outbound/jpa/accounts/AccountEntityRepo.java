package com.example.bankingservice.outbound.jpa.accounts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountEntityRepo extends JpaRepository<AccountEntity, Long> {

    @Query("select acc from AccountEntity acc join acc.owner o  where o.UUID = :ownerID ")
    List<AccountEntity> findAllByOwner(@Param("ownerID") Long ownerID);


    @Query(value = "select acc.* from accounts acc where acc.card_number=?1",
            nativeQuery = true)
    Optional<AccountEntity> findByCardNumber(String cardNumber);


    @Query(value = "select acc.* from accounts acc, customers c where c.uuid=acc.ownerid and acc.card_number= ?1 and acc.ownerid=?2", nativeQuery = true)
    Optional<AccountEntity> findByOwnerAndPhysicalCard(String CardNumber, Long UUID);

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}

