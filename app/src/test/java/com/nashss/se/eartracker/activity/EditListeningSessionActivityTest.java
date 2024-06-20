package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.DeleteListeningSessionRequest;
import com.nashss.se.eartracker.activity.request.EditListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.DeleteListeningSessionResult;
import com.nashss.se.eartracker.activity.result.EditListeningSessionResult;
import com.nashss.se.eartracker.calculator.TimeElapsedCalculator;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditListeningSessionActivityTest {
    @Mock
    private  ListeningSessionDao listeningSessionDao;

    @Mock
    private TimeElapsedCalculator timeElapsedCalculator;

    private EditListeningSessionActivity editListeningSessionActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        editListeningSessionActivity = new EditListeningSessionActivity(listeningSessionDao);
        timeElapsedCalculator = new TimeElapsedCalculator();
    }

//    @Test
//    void handleRequest_validRequest_updatesTracker() {
//        // GIVEN
//        String email = "validEmail@email.com";
//        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
//        LocalDateTime newStartSession = LocalDateTime.of(2024, 6, 4, 10,10,10);
//        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
//        String listeningType = "Spotify";
//        String notes ="";
//
//        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
//                .withEmail(email)
//                .withStartSession(startSession)
//                .withNewStartSession(newStartSession)
//                .withEndSession(endSession)
//                .withListeningType(listeningType)
//                .withNotes(notes)
//                .build();
//
//        // WHEN
//        EditListeningSessionResult result = editListeningSessionActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(email, result.getListeningSession().getEmail());
//        assertEquals(newStartSession, result.getListeningSession().getStartSession());
//        assertEquals(endSession, result.getListeningSession().getEndSession());
//        assertEquals(listeningType, result.getListeningSession().getListeningType());
//        assertEquals(notes, request.getNotes());
//    }

    @Test
    void handleRequest_invalidEmail_throwsInvalidAttributeException() {
        // GIVEN
        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
                .withEmail(null)
                .build();

        // THEN
        assertThrows(InvalidAttributeValueException.class, () -> editListeningSessionActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_invalidListeningType_throwsInvalidAttributeValueException(){
        // GIVEN
        String email = "validEmail@gmail.com";
        String listeningType = "'@notrealListeningType'";

        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
                .withEmail(email)
                .withListeningType(listeningType)
                .build();

        // WHEN + THEN
        assertThrows(InvalidAttributeValueException.class, () -> editListeningSessionActivity.handleRequest(request));
    }
}
