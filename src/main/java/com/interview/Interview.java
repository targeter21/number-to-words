package com.interview;

import java.util.Arrays;

public class Interview {
    public static void main(String[] args) {
        NumberConverter numberConverter = new NumberConverterImpl();

        Arrays.stream(args).forEach(arg -> {
            String convertedNumber = numberConverter.convertNumberToWords(arg);

            if (convertedNumber.isEmpty()) {
                System.out.println("Invalid value: " + arg);
            }
            else {
                System.out.println(convertedNumber);
            }
        });
    }
}
