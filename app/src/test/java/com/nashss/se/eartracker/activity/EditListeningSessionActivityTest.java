package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.EditListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.EditListeningSessionResult;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditListeningSessionActivityTest {
    @Mock
    private ListeningSessionDao listeningSessionDao;

    private EditListeningSessionActivity editListeningSessionActivity;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        editListeningSessionActivity = new EditListeningSessionActivity(listeningSessionDao);
    }

//    @Test
//    public void handleRequest_goodRequest_editsListeningSession() {
//        // GIVEN
//        String email = "validEmail@email.com";
//        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
//        LocalDateTime newStartSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
//        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 18, 0, 0);
//        String listeningType = "Work";
//        String notes = "Zoom meeting";
//
//        String oldListeningType = "Spotify";
//
//        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
//                .withEmail(email)
//                .withStartSession(startSession)
//                .withNewStartSession(startSession)
//                .withEndSession(endSession)
//                .withListeningType(listeningType)
//                .withNotes(notes)
//                .build();
//
//        ListeningSession startingListeningSession = new ListeningSession();
//        startingListeningSession.setEmail(email);
//        startingListeningSession.setStartSession(LocalDateTime.of(2024, 6, 4, 12, 30, 30));
//        startingListeningSession.setEndSession(endSession);
//        startingListeningSession.setListeningType(oldListeningType);
//        startingListeningSession.setNotes(notes);
//
//        when(listeningSessionDao.getListeningSession(email, startSession)).thenReturn(startingListeningSession);
//        when(listeningSessionDao.saveListeningSession(startingListeningSession)).thenReturn(startingListeningSession);
//
//        // WHEN
//        EditListeningSessionResult result = editListeningSessionActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(newStartSession, result.getListeningSession().getStartSession());
//        assertEquals(listeningType, result.getListeningSession().getListeningType());
//        assertEquals(request.getEndSession(), result.getListeningSession().getEndSession());
//    }

    @Test
    public void handleRequest_customerIdNotMatch_throwsSecurityException() {
        // GIVEN
        String email = "firstEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);

        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
                .withEmail(email)
                .withStartSession(startSession)
                .build();

        ListeningSession differentEmail = new ListeningSession();
        differentEmail.setEmail("differentEmail@email.com");

        when(listeningSessionDao.getListeningSession(email, startSession)).thenReturn(differentEmail);

        // WHEN + THEN
        assertThrows(SecurityException.class, () -> editListeningSessionActivity.handleRequest(request));
    }
}
