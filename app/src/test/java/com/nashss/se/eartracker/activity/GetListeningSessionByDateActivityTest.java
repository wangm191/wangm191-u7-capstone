package com.nashss.se.eartracker.activity;

import com.nashss.se.eartracker.activity.request.GetListeningSessionByDateRequest;
import com.nashss.se.eartracker.dynamodb.ListeningSessionDao;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;

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

//    @Test
//    void handleRequest_listeningSessionExists_returnsListeningSession(){
//        // GIVEN
//        ListeningSession listeningSession = new ListeningSession();
//        String email = "validEmail@email.com";
//        LocalDateTime startSession = LocalDateTime.of(2024, 6, 4, 12, 30, 30);
//
//        GetListeningSessionByDateRequest request = GetListeningSessionByDateRequest.builder()
//                .withEmail(email)
//    }
}
