package com.interview;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will take a number as a string and convert it to
 * its English word equivalent.
 */
public class NumberConverterImpl implements NumberConverter {
    /**
     * This function takes a number in string format and converts it to English word equivalents.
     * For example, 5237 would result in Five thousand two hundred and thirty seven.
     *
     * @param numberString String comprising only of numeric digits. Prepending 0's will be ignored.
     *
     * @return The given number string as its English word equivalents, or empty string if an
     *         invalid value is given.
     */
    @Override
    public String convertNumberToWords(String numberString) {
        StringBuilder stringBuilder = new StringBuilder();

        // Eliminates appending 0's
        try {
            Integer numberToConvert = Integer.valueOf(numberString);
            numberString = String.valueOf(numberToConvert);

            // This is the only time Zero is printed so we can shortcut.
            if (numberToConvert == 0) {
                stringBuilder.append("zero");
            } else {
                final List<String> numberChunks = new ArrayList<>();
                final int chunkSize = 3;

                // A negative number is printed the same way, just with the word 'Negative'
                if (numberToConvert < 0) {
                    stringBuilder.append("negative ");
                    numberString = numberString.substring(1);
                }

                // Group the number into chunks of 3 so we can break the problem down
                // and reuse code. We start from the right because numbers won't always
                // be perfect divisible-by-three numbers.
                for (int i = numberString.length(); i > 0; i -= chunkSize) {
                    int leftIndex = Math.max(i - chunkSize, 0);     // Prevents index out of bounds errors
                    numberChunks.add(numberString.substring(leftIndex, i));
                }

                for (int i = numberChunks.size() - 1; i >= 0; i--) {
                    String numberChunk = numberChunks.get(i);
                    int potentialHundredsNumber = Character.getNumericValue(numberChunk.charAt(0));

                    // First handle potential hundreds
                    if (numberChunk.length() == chunkSize && potentialHundredsNumber != 0) {
                        String hundredsPhrase = getPowerOfTenPhrase(2);       // Always used when a number grouping is 3 digits in size
                        String underTwentyPhrase = getUnderTwentyPhrase(potentialHundredsNumber);

                        stringBuilder.append(underTwentyPhrase).append(" ").append(hundredsPhrase).append(" ");
                    }

                    // Next handle the tens position
                    int tensNumber = Integer.valueOf(numberChunk) % 100;
                    String underTwentyPhrase;

                    if (tensNumber >= 20) {
                        // Only use the word "and" at the end.
                        if (i == 0) {
                            stringBuilder.append("and ");
                        }

                        String tensPhrase = getTensPhrase(tensNumber);
                        stringBuilder.append(tensPhrase).append(" ");

                        underTwentyPhrase = getUnderTwentyPhrase(tensNumber % 10);
                    } else {
                        underTwentyPhrase = getUnderTwentyPhrase(tensNumber);
                    }

                    // If the ones position is 0, then the result will be empty
                    // so don't print the empty string or extra space.
                    if (!underTwentyPhrase.isEmpty()) {
                        stringBuilder.append(underTwentyPhrase).append(" ");
                    }

                    // The first three digits in a number can only say "hundred" and never
                    // have an additional modifier at the end.
                    if (i != 0) {
                        String placeModifierPhrase = getPowerOfTenPhrase(i + 2);
                        stringBuilder.append(placeModifierPhrase).append(" ");
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            return "";
        }

        // Format it nicely
        return Character.toUpperCase(stringBuilder.charAt(0)) + stringBuilder.substring(1).trim();
    }

    /**
     * This function is made to process non-zero numbers under 20 and return
     * that number back as its English word.
     *
     * @param underTwentyNumber A non-zero number ranging from 1-19.
     *
     * @return The English word equivalent of the given number, or empty string.
     */
    private String getUnderTwentyPhrase(int underTwentyNumber) {
        // Zero is a special case that is only printed if the number is exactly 0.
        String[] underTwentyPhrases = { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven",
                "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };

        return underTwentyNumber >= underTwentyPhrases.length ? "" : underTwentyPhrases[underTwentyNumber];
    }

    /**
     * This function is made to process non-zero numbers that are multiples of 10 and under 100.
     *
     * @param tensNumber A non-zero number that is one of the following: 10, 20, 30, 40, 50,
     *                   60, 70, 80, and 90.
     *
     * @return The English word equivalent of the given number, or empty string.
     */
    private String getTensPhrase(int tensNumber) {
        // Ten is easier handled as part of the ones logic so we can omit that
        // here and we pad the first index because 0 does not exist as a multiple
        // of ten.
        String[] tens = { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
        String phrase = "";

        if (tensNumber / 10 < tens.length) {
            phrase = tens[tensNumber / 10];
        }

        return phrase;
    }

    /**
     * Returns the correct English phrase for a given power of ten. For example,
     * 2 will return 'Hundred' because 10^2 is 100.
     *
     * @param exponent Power of ten exponent that should correspond to the desired phrase.
     *
     * @return The English word equivalent of a power of ten's exponent, or empty string.
     */
    private String getPowerOfTenPhrase(int exponent) {
        // Array is 0-indexed
        exponent--;

        // Ones and Tens are never spoken in the English language as modifiers so we can omit them.
        String[] placeModifiers = { "", "hundred", "thousand", "million", "billion" };

        return exponent >= placeModifiers.length ? "" : placeModifiers[exponent];
    }
}
