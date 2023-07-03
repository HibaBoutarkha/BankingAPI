package com.example.bankingservice.domain.utils;

import com.example.bankingservice.domain.base.Account;
import com.example.bankingservice.domain.base.PhysicalCard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AccountGenerator {

    private final static String ribRegex = "^\\d{16}$";
    private final static String accountNumber = "^\\d{10}$";
    private static CardGenerator cardGenerator;


    public static Account generateAccount(){
        Account account = new Account();

        account.setAccountNumber(generateAccountNumber());
        account.setRib(generateRIB());
        account.setIsActive(true);

        PhysicalCard physicalCard = (PhysicalCard) cardGenerator.generateCard("physical");
        account.setPhysicalCard(physicalCard);

        account.setVirtualCardList(new ArrayList<>());
        return account;
    }

    private static String generateRIB(){
        Pattern pattern = Pattern.compile(ribRegex);
        int length = 16;
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        while(true) {
            for (int i = 0; i < length; i++) {
                int digit = random.nextInt(10); // Generate a random digit from 0 to 9
                stringBuilder.append(digit);
            }

            String generatedString = stringBuilder.toString();

            Matcher matcher = pattern.matcher(generatedString);
            if (matcher.matches()) {
                return generatedString;
            }
        }
    }

    private static String generateAccountNumber(){
        Pattern pattern = Pattern.compile(accountNumber);
        int length = 10;
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        while(true) {
            for (int i = 0; i < length; i++) {
                int digit = random.nextInt(10); // Generate a random digit from 0 to 9
                stringBuilder.append(digit);
            }

            String generatedString = stringBuilder.toString();

            Matcher matcher = pattern.matcher(generatedString);
            if (matcher.matches()) {
                return generatedString;
            }
        }
    }
}
