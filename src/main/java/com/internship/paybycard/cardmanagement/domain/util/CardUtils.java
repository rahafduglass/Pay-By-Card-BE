package com.internship.paybycard.cardmanagement.domain.util;

import java.util.Random;

public class CardUtils {
    private final static Random RANDOM = new Random();
    public static String generateCardNumber(Long cardId) {
        StringBuilder randomCapitals= new StringBuilder();
        for(int i=1;i<=10;i++) {
            randomCapitals.append((char) (RANDOM.nextInt(65, 91)));
        }
        return cardId + "0000" +randomCapitals;
    }

    public static String generateCVV() {
        int randomCVV= (int) (RANDOM.nextInt(100,999));
        return String.valueOf(randomCVV);
    }
}
//take the id and thennnn generate random characters :D