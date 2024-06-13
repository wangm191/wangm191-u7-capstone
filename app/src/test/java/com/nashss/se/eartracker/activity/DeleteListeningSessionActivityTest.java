package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.DeleteListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.DeleteListeningSessionResult;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeleteListeningSessionActivityTest {
    @Mock
    private  ListeningSessionDao listeningSessionDao;

    private DeleteListeningSessionActivity deleteListeningSessionActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        deleteListeningSessionActivity = new DeleteListeningSessionActivity(listeningSessionDao);
    }

    @Test
    void handleRequest_validRequest_deletesTracker() {
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);

        String listeningType = "Spotify";

        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail(email);
        listeningSession.setStartSession(startSession);
        listeningSession.setEndSession(endSession);
        listeningSession.setListeningType(listeningType);

        when(listeningSessionDao.getListeningSession(listeningSession.getEmail(), listeningSession.getStartSession())).thenReturn(listeningSession);
        when(listeningSessionDao.deleteListeningSession(listeningSession)).thenReturn(listeningSession);

        DeleteListeningSessionRequest request = DeleteListeningSessionRequest.builder()
                .withEmail(listeningSession.getEmail())
                .withStartSession(listeningSession.getStartSession())
                .withListeningType(listeningSession.getListeningType())
                .build();

        // WHEN
        DeleteListeningSessionResult result = deleteListeningSessionActivity.handleRequest(request);

        // THEN
        assertEquals(listeningSession.getEmail(), result.getListeningSession().getEmail());
        assertEquals(listeningSession.getStartSession(), result.getListeningSession().getStartSession());
        assertEquals(listeningSession.getListeningType(), result.getListeningSession().getListeningType());
    }

    @Test
    void handleRequest_invalidEmail_throwsInvalidAttributeException() {
        // GIVEN
        DeleteListeningSessionRequest request = DeleteListeningSessionRequest.builder()
                .withEmail(null)
                .build();

        // THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> deleteListeningSessionActivity.handleRequest(request));
    }
}
