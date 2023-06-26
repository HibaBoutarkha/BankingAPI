package com.example.bankingservice.domain.business.usecases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCardStatusRequest {

    private Long uuid;
    private String cardNumber;
    private String cardType;
}
