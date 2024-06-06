package com.nashss.se.eartracker.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.MockitoAnnotations.openMocks;

public class TimeElapsedCalculatorTest {
    private TimeElapsedCalculator timeElapsedCalculator;

    @BeforeEach
    void setup(){
        openMocks(this);
        timeElapsedCalculator = new TimeElapsedCalculator();
    }

    @Test
    public void handleRequest_withValidLocalDateTime_convertsToString(){
        // GIVEN
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
        String timeElapsed = "3:29:15";

        // WHEN
        String result = timeElapsedCalculator.handleRequest(startSession, endSession);
        System.out.println(result);

        // THEN
        assertEquals(timeElapsed, result);
    }
}
