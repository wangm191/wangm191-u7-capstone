package com.nashss.se.eartracker.calculator;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeElapsedCalculator {

    public LocalDateTime startSession;
    public LocalDateTime endSession;
    public Duration duration;

    public TimeElapsedCalculator(LocalDateTime startSession, LocalDateTime endSession) {
        this.startSession = startSession;
        this.endSession = endSession;
    }

    public String handleRequest(final LocalDateTime startSession, final LocalDateTime endSession) {
         duration = Duration.between(startSession, endSession);
         return duration.toString();

    }

    @Override
    public String toString() {
        return duration.toHoursPart() + " hours, " +
                duration.toMinutesPart() + " minutes, " +
                duration.toSecondsPart() + " seconds";
    }
}
