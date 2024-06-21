package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.GetListeningSessionByDateRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByDateResult;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetListeningSessionByDateActivityTest {
    @Mock
    private ListeningSessionDao listeningSessionDao;

    private GetListeningSessionByDateActivity getListeningSessionByDateActivity;

    @BeforeEach
    void setup(){
        openMocks(this);
        getListeningSessionByDateActivity = new GetListeningSessionByDateActivity(listeningSessionDao);
    }

    @Test
    void handleRequest_listeningSessionExists_returnsListeningSession(){
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime startSessionStart = startSession.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime startSessionEnd = startSession.withHour(23).withMinute(59).withSecond(59);

        ListeningSession listeningSession = new ListeningSession();
        List<ListeningSession> listeningSessionList = new ArrayList<>();

        listeningSession.setEmail(email);
        listeningSession.setStartSession(startSession);

        listeningSessionList.add(listeningSession);

        when(listeningSessionDao.searchListeningSessionByDate(listeningSession.getEmail(), startSessionStart, startSessionEnd)).thenReturn(listeningSessionList);

        GetListeningSessionByDateRequest request = GetListeningSessionByDateRequest.builder()
                .withEmail(email)
                .withStartSession(startSession)
                .build();

        GetListeningSessionByDateResult result = getListeningSessionByDateActivity.handleRequest(request);

        assertEquals(listeningSessionList.size(), result.getListeningSessions().size());
        assertEquals(listeningSessionList.get(0).getEmail(), result.getListeningSessions().get(0).getEmail());
        assertEquals(listeningSessionList.get(0).getStartSession(), result.getListeningSessions().get(0).getStartSession());
        assertEquals(listeningSessionList.get(0).getEndSession(), result.getListeningSessions().get(0).getEndSession());
    }

    @Test
    public void handleRequest_notMatchingStartSession_throwsListeningSessionNotFoundException(){
        // GIVEN
        String email = "validEmail@email.com";
        LocalDateTime invalidStartSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime invalidStartSessionStart = invalidStartSession.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime invalidStartSessionEnd = invalidStartSessionStart.withHour(23).withMinute(59).withSecond(59);

        GetListeningSessionByDateRequest request = GetListeningSessionByDateRequest.builder()
                .withEmail(email)
                .withStartSession(invalidStartSession)
                .build();

        when(listeningSessionDao.searchListeningSessionByDate(email, invalidStartSessionStart, invalidStartSessionEnd)).thenThrow(ListeningSessionNotFoundException.class);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> getListeningSessionByDateActivity.handleRequest(request));
    }

    @Test
    public void handleRequest_nullEmail_throwsListeningSessionNotFoundException(){
        // GIVEN
        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
        LocalDateTime startSessionStart = startSession.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime startSessionEnd = startSession.withHour(23).withMinute(59).withSecond(59);

        GetListeningSessionByDateRequest request = GetListeningSessionByDateRequest.builder()
                .withEmail(null)
                .withStartSession(startSession)
                .build();

        when(listeningSessionDao.searchListeningSessionByDate(null, startSessionStart, startSessionEnd)).thenThrow(ListeningSessionNotFoundException.class);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> getListeningSessionByDateActivity.handleRequest(request));
    }
}
