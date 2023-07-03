package com.example.bankingservice.domain.business.usecases;

import com.example.bankingservice.domain.base.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String phoneNumber;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthdate;
    private Address birthAddress;
    private Address agencyAddress;

}
