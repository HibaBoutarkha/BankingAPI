package com.example.bankingservice.domain.business.usecases;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountInfoRequest {

    private Long UUID;
}
