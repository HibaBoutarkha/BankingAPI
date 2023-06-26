package com.example.bankingservice.domain.business.usecases;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface SignUp {

    public ResponseEntity handle(@RequestBody SignUpRequest request);
}
