package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.activity.request.AddListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.AddListeningSessionResult;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddListeningSessionActivityTest {

    @Mock
    private ListeningSessionDao listeningSessionDao;

    private AddListeningSessionActivity addListeningSessionActivity;

    @BeforeEach
    void setup(){
        openMocks(this);
        addListeningSessionActivity = new AddListeningSessionActivity(listeningSessionDao);
    }

    @Test
    public void handleRequest_withValidEmail_AddsListeningSession(){
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
        String listeningType = "Spotify";
        String notes = "";

        AddListeningSessionRequest request = AddListeningSessionRequest.builder()
                .withEmail(email)
                .withStartSession(startSession)
                .withEndSession(endSession)
                .withListeningType(listeningType)
                .withNotes(notes)
                .build();

        // WHEN
        AddListeningSessionResult result = addListeningSessionActivity.handleRequest(request);

        // THEN
        verify(listeningSessionDao).saveListeningSession(any(ListeningSession.class));

        assertEquals(email, result.getListeningSessionModel().getEmail());
        assertEquals(startSession, result.getListeningSessionModel().getStartSession());
        assertEquals(endSession, result.getListeningSessionModel().getEndSession());
        assertEquals(listeningType, result.getListeningSessionModel().getListeningType());
        assertEquals(notes, result.getListeningSessionModel().getNotes());
    }

    @Test
    public void handleRequest_nullNotes_AddsListeningSession(){
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
        String listeningType = "Spotify";

        AddListeningSessionRequest request = AddListeningSessionRequest.builder()
                .withEmail(email)
                .withStartSession(startSession)
                .withEndSession(endSession)
                .withListeningType(listeningType)
                .withNotes(null)
                .build();

        // WHEN
        AddListeningSessionResult result = addListeningSessionActivity.handleRequest(request);

        verify(listeningSessionDao).saveListeningSession(any(ListeningSession.class));

        assertEquals(email, result.getListeningSessionModel().getEmail());
        assertEquals(startSession, result.getListeningSessionModel().getStartSession());
        assertEquals(endSession, result.getListeningSessionModel().getEndSession());
        assertEquals(listeningType, result.getListeningSessionModel().getListeningType());
        assertEquals("", result.getListeningSessionModel().getNotes());
    }

    @Test
    public void handleRequest_nullEmail_throwsInvalidAttributeValueException(){

        AddListeningSessionRequest request = AddListeningSessionRequest.builder()
                .withEmail(null)
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> addListeningSessionActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidListeningType_throwsInvalidAttributeValueException(){
        // GIVEN
        String email = "validEmail@gmail.com";
        String listeningType = "'@notrealListeningType'";

        AddListeningSessionRequest request = AddListeningSessionRequest.builder()
                .withEmail(email)
                .withListeningType(listeningType)
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> addListeningSessionActivity.handleRequest(request));
    }
}
