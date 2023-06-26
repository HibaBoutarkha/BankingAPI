package com.example.bankingservice.domain.business.usecases;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface Login {

    public ResponseEntity handle(@RequestBody LoginRequest request);
}
