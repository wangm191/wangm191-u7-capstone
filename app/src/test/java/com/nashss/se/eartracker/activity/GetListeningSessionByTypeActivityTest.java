package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.GetListeningSessionByTypeRequest;
import com.nashss.se.eartracker.activity.result.GetListeningSessionByTypeResult;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.exceptions.ListeningSessionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetListeningSessionByTypeActivityTest {
    @Mock
    private ListeningSessionDao listeningSessionDao;

    private GetListeningSessionByTypeActivity getListeningSessionByTypeActivity;

    @BeforeEach
    void setup(){
        openMocks(this);
        getListeningSessionByTypeActivity = new GetListeningSessionByTypeActivity(listeningSessionDao);
    }

    @Test
    void handleRequest_listeningSessionExists_returnsListeningSession(){
        // GIVEN
        String email = "validEmail@email.com";
        String listeningType = "Spotify";

        ListeningSession listeningSession = new ListeningSession();
        List<ListeningSession> listeningSessionList = new ArrayList<>();

        listeningSession.setEmail(email);
        listeningSession.setListeningType(listeningType);

        listeningSessionList.add(listeningSession);

        when(listeningSessionDao.searchListeningSessionByListeningType(listeningSession.getEmail(), listeningSession.getListeningType())).thenReturn(listeningSessionList);

        GetListeningSessionByTypeRequest request = GetListeningSessionByTypeRequest.builder()
                .withEmail(email)
                .withListeningType(listeningType)
                .build();

        GetListeningSessionByTypeResult result = getListeningSessionByTypeActivity.handleRequest(request);

        assertEquals(listeningSessionList.size(), result.getListeningSessions().size());
        assertEquals(listeningSessionList.get(0).getEmail(), result.getListeningSessions().get(0).getEmail());
        assertEquals(listeningSessionList.get(0).getListeningType(), result.getListeningSessions().get(0).getListeningType());
    }

    @Test
    public void handleRequest_nullEmail_throwsListeningSessionNotFoundException(){
        // GIVEN
        String listeningType = "Zoom";

        GetListeningSessionByTypeRequest request = GetListeningSessionByTypeRequest.builder()
                .withEmail(null)
                .withListeningType(listeningType)
                .build();

        when(listeningSessionDao.searchListeningSessionByListeningType(null, listeningType)).thenThrow(ListeningSessionNotFoundException.class);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> getListeningSessionByTypeActivity.handleRequest(request));
    }
    @Test
    public void handleRequest_notMatchingListeningType_throwsListeningSessionNotFoundException(){
        // GIVEN
        String email = "validEmail@email.com";
        String invalidListeningType ="fakeListeningType";

        GetListeningSessionByTypeRequest request = GetListeningSessionByTypeRequest.builder()
                .withEmail(email)
                .withListeningType(invalidListeningType)
                .build();

        when(listeningSessionDao.searchListeningSessionByListeningType(email, invalidListeningType)).thenThrow(ListeningSessionNotFoundException.class);

        // WHEN + THEN
        assertThrows(ListeningSessionNotFoundException.class, () -> getListeningSessionByTypeActivity.handleRequest(request));
    }
}
