package com.example.bankingservice.configurations;

import com.example.bankingservice.domain.utils.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomErrorHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if(ex instanceof CustomException) {
            String message = "{\"message\":\""+ex.getMessage()+"\"}";


            return ResponseEntity.status(((CustomException) ex).getCode())
                    .headers(headers)
                    .body(message)
                    ;
        }

        else {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .headers(headers)
                .body("{\"message\": \"Oops, something went wrong try again later\"}");}
    }
}
