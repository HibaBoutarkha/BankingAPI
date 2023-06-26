package com.example.bankingservice.outbound.jpa.virtualcards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VirtualCardEntityRepo extends JpaRepository<VirtualCardEntity, Long> {

    Optional<VirtualCardEntity> findByCardNumber(String cardNumber);

    @Query("select c from VirtualCardEntity c join c.account a join a.owner o " +
            "where c.cardNumber=:cardNumber and o.UUID=:UUID")
    Optional<VirtualCardEntity> findByCardNumberAndOwner(@Param("cardNumber") String cardNumber, @Param("UUID") Long UUID);

    VirtualCardEntity save(VirtualCardEntity entity);
}
