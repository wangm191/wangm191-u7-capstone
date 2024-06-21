package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.EditListeningSessionRequest;
import com.nashss.se.eartracker.activity.result.EditListeningSessionResult;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.InvalidAttributeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditListeningSessionActivityTest {
    @Mock
    private  ListeningSessionDao listeningSessionDao;

    @Mock
    private EditListeningSessionActivity editListeningSessionActivity;

    @BeforeEach
    void setup() {
        openMocks(this);
        editListeningSessionActivity = new EditListeningSessionActivity(listeningSessionDao);
    }

//    @Test
//    void handleRequest_validRequest_updatesListeningSession() {
//        // GIVEN
//        String email = "validEmail@email.com";
//        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
//        LocalDateTime newStartSession = LocalDateTime.of(2024, 6, 4, 13,  45, 45);
//        LocalDateTime endSession = LocalDateTime.of(2024, 6, 4, 15, 59, 45);
//        String listeningType = "Spotify";
//        String newListeningType = "Zoom";
//
//        EditListeningSessionRequest request = EditListeningSessionRequest.builder()
//                .withEmail(email)
//                .withStartSession(startSession)
//                .withNewStartSession(newStartSession)
//                .withEndSession(endSession)
//                .withListeningType(newListeningType)
//                .build();
//
//        ListeningSession startingListeningSession = new ListeningSession();
//        startingListeningSession.setEmail(email);
//        startingListeningSession.setStartSession(startSession);
//        startingListeningSession.setEndSession(endSession);
//        startingListeningSession.setListeningType(listeningType);
//
//        when(listeningSessionDao.getListeningSession(startingListeningSession.getEmail(), startingListeningSession.getStartSession())).thenReturn(startingListeningSession);
//        when(listeningSessionDao.saveListeningSession(startingListeningSession)).thenReturn(startingListeningSession);
//
//        //doReturn(startSession). when(listeningSessionDao.getListeningSession(startingListeningSession.getEmail(), startingListeningSession.getStartSession()));
//
//        // WHEN
//        EditListeningSessionResult result = editListeningSessionActivity.handleRequest(request);
//
//        // THEN
//        assertEquals(startingListeningSession.getEmail(), result.getListeningSession().getEmail());
//        assertEquals(newStartSession, result.getListeningSession().getStartSession());
//        assertEquals(newListeningType, result.getListeningSession().getListeningType());
//    }

    @Test
    void handleRequest_nullEmail_throwsInvalidAttributeException() {
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
