package com.nashss.se.eartracker.calculator;

import dagger.Provides;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeElapsedCalculator {

    public Duration duration;

    public TimeElapsedCalculator() {}

    public String handleRequest(LocalDateTime startSession, LocalDateTime endSession) {
         duration = Duration.between(startSession, endSession);
         return String.format("%d:%d:%d", duration.toHoursPart(), duration.toMinutesPart(), duration.toSecondsPart());
    }
}
