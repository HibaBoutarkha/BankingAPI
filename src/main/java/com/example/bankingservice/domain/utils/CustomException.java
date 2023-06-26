package com.example.bankingservice.domain.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends Exception {
    private int code;

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

}
