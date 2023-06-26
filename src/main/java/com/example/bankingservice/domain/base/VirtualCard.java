package com.example.bankingservice.domain.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirtualCard extends Card {

    private String id;

}
