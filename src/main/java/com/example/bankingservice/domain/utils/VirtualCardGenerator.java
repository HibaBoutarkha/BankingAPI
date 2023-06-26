package com.example.bankingservice.domain.utils;

import com.example.bankingservice.domain.base.VirtualCard;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VirtualCardGenerator {

    private static final String cardNumberRegex = "^5[1-5][0-9]{14}$";
    private static final String cvcRegex = "^[0-9]{3}$";
    private static final long EXPIRATION_TIME = 126200000L;


    public static VirtualCard generateVirtualCard() {
        VirtualCard virtualCard = new VirtualCard();

        Date creationDate = new Date();
        Date expirationDate = calculateExpirationDate(creationDate, EXPIRATION_TIME);

        virtualCard.setIsEnabled(true);
        virtualCard.setCreationDate(creationDate);
        virtualCard.setExpirationDate(expirationDate);
        virtualCard.setCardNumber(generateCardNumber(cardNumberRegex));
        virtualCard.setCvc(generateCVC(cvcRegex));


        return virtualCard;
    }

    private static String generateCardNumber(String cardNumberRegex) {
        Pattern pattern = Pattern.compile(cardNumberRegex);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("5");
        while(true) {
            sb.append((char) (random.nextInt(5) + '1'));
            for (int i = 0; i < 14; i++) {
                sb.append((char) (random.nextInt(10) + '0'));
            }

            String generatedString = sb.toString();

            Matcher matcher = pattern.matcher(generatedString);
            if (matcher.matches()) {
                return generatedString;
            }
        }
    }

    private static String generateCVC(String cvcRegex) {
        Pattern pattern = Pattern.compile(cvcRegex);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while(true) {
            for (int i = 0; i < 3; i++) {
                sb.append((char) (random.nextInt(10) + '0'));
            }

            String generatedString = sb.toString();

            Matcher matcher = pattern.matcher(generatedString);
            if (matcher.matches()) {
                return generatedString;
            }
        }
    }


    private static Date calculateExpirationDate(Date givenDate, long expirationTimeInMillis) {
        // Convert the given date to Calendar for date manipulation
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(givenDate);

        // Add the expiration time in milliseconds to the given date
        calendar.add(Calendar.MILLISECOND, (int) expirationTimeInMillis);

        // Obtain the expiration date
        return calendar.getTime();
    }
}
