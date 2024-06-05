package com.nashss.se.eartracker.converters;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LocalDateTimeConverterTest {

    @Test
    void unconvert_givenString_returnsLocalDate() {
        // GIVEN
        String dateTime = "2024-06-04T12:30:30";
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        LocalDateTime expected = LocalDateTime.of(2024, 6, 4, 12, 30, 30);

        // WHEN
        LocalDateTime result = converter.unconvert(dateTime);

        // THEN
        assertEquals(expected, result);
    }

    @Test
    void convert_givenDate_returnsMonthAsString() {
        // GIVEN
        LocalDateTime dateTime = LocalDateTime.of(2024, 5, 25, 8, 25, 10);
        LocalDateTimeConverter converter = new LocalDateTimeConverter();
        String expectedString = "2024-05-25T08:25:10";

        // WHEN
        String result = converter.convert(dateTime);

        // THEN
        assertEquals(expectedString, result);
    }
}
