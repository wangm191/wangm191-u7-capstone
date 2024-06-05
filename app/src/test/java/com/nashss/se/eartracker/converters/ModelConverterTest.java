package com.nashss.se.eartracker.converters;

import com.nashss.se.eartracker.models.ListeningSessionModel;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toListeningSessionModel_withTags_convertsListeningSession() {
        String timeElapsed = "3 hours, 29 minutes, 15 seconds";

        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail("validEmail@email.com");
        listeningSession.setStartSession(LocalDateTime.of(2024, 6, 4, 12, 30, 30));
        listeningSession.setEndSession(LocalDateTime.of(2024, 6, 4, 15, 59, 45));
        listeningSession.setListeningType("Spotify");
        listeningSession.setNotes("");

        ListeningSessionModel listeningSessionModel = modelConverter.toListeningSessionModel(listeningSession);

        assertEquals(listeningSession.getEmail(), listeningSessionModel.getEmail());
        assertEquals(listeningSession.getStartSession(), listeningSessionModel.getStartSession());
        assertEquals(listeningSession.getEndSession(), listeningSessionModel.getEndSession());
        assertEquals(listeningSession.getListeningType(), listeningSessionModel.getListeningType());
        assertEquals(listeningSession.getNotes(), listeningSessionModel.getNotes());
    }

    @Test
    void toListeningSessionModel_nullNotes_convertsListeningSession() {
        String timeElapsed = "3 hours, 29 minutes, 15 seconds";

        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail("validEmail@email.com");
        listeningSession.setStartSession(LocalDateTime.of(2024, 6, 4, 12, 30, 30));
        listeningSession.setEndSession(LocalDateTime.of(2024, 6, 4, 15, 59, 45));
        listeningSession.setListeningType("Spotify");
        listeningSession.setNotes(null);

        ListeningSessionModel listeningSessionModel = modelConverter.toListeningSessionModel(listeningSession);

        assertEquals(listeningSession.getEmail(), listeningSessionModel.getEmail());
        assertEquals(listeningSession.getStartSession(), listeningSessionModel.getStartSession());
        assertEquals(listeningSession.getEndSession(), listeningSessionModel.getEndSession());
        assertEquals(listeningSession.getListeningType(), listeningSessionModel.getListeningType());
        assertNull(listeningSessionModel.getNotes());
    }
}
