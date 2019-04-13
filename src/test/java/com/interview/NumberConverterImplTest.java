package com.interview;

import com.interview.NumberConverter;
import com.interview.NumberConverterImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NumberConverterImplTest {
    NumberConverter numberConverter = new NumberConverterImpl();

    @Test
    public void testMaxValue() {
        String maxValueWords = "Two billion one hundred forty seven million four hundred eighty three thousand six hundred and forty seven";
        String number = String.valueOf(Integer.MAX_VALUE);

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }

    @Test
    public void testMinValue() {
        String maxValueWords = "Negative two billion one hundred forty seven million four hundred eighty three thousand six hundred and forty eight";
        String number = String.valueOf(Integer.MIN_VALUE);

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }

    @Test
    public void testZeroValue() {
        String maxValueWords = "Zero";
        String number = "0";

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }

    @Test
    public void testAppendingZeroValue() {
        String maxValueWords = "One thousand three hundred and thirty seven";
        String number = "0001337";

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }

    @Test
    public void testInvalidValue() {
        String maxValueWords = "";
        String number = "dwad0001337";

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }

    @Test
    public void testNull() {
        String maxValueWords = "";
        String number = null;

        assertEquals(numberConverter.convertNumberToWords(number), maxValueWords);
    }
}
