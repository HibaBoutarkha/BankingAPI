package com.example.bankingservice.domain.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUser {

    private Long UUID;
    private String phoneNumber;
    private String password;
    private String role;


}
