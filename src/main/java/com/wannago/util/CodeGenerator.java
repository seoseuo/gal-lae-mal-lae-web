package com.wannago.util;

import java.security.SecureRandom;
import java.util.Random;

public class CodeGenerator {

    public static String generateNumericCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);  // 0부터 9까지의 랜덤 숫자
            code.append(digit);
        }
        return code.toString();
    }

    //문자열 랜덤 생성
    public static String generateRandomString(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }
}

