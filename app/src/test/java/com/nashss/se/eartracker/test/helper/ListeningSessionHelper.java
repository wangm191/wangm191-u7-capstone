package com.nashss.se.eartracker.test.helper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.nashss.se.eartracker.dynamodb.models.ListeningSession;

public final class ListeningSessionHelper {
    private ListeningSessionHelper(){
    }

    public static ListeningSession generateTracker() {
        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail("email@email.com");
        listeningSession.setStartSession(LocalDateTime.of(2024, 6, 4, 12, 30, 30));
        listeningSession.setEndSession(LocalDateTime.of(2024, 6, 4, 15, 59, 45));
        listeningSession.setListeningType("Spotify");
        listeningSession.setListeningType("1 hours, 29 minutes, 15 seconds" );
        listeningSession.setNotes("");
        return listeningSession;
    }
}
