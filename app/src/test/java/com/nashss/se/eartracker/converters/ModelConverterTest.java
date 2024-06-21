package com.nashss.se.eartracker.converters;

import com.nashss.se.eartracker.dynamodb.models.ListeningType;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import com.nashss.se.eartracker.dynamodb.models.ListeningSession;

import com.nashss.se.eartracker.models.ListeningTypeModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toListeningSessionModel_withValidAttributes_convertsListeningSession() {
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

    @Test
    void toListeningTypeModel_withValidAttributes_convertsListeningType(){
        ListeningType listeningType = new ListeningType();
        listeningType.setEmail("validEmail@email.com");
        listeningType.setListeningType("validListeningType");
        listeningType.setNotes("");

        ListeningTypeModel listeningTypeModel = modelConverter.toListeningTypeModel(listeningType);

        assertEquals(listeningType.getEmail(), listeningTypeModel.getEmail());
        assertEquals(listeningType.getListeningType(), listeningTypeModel.getListeningType());
        assertEquals(listeningType.getNotes(), listeningTypeModel.getNotes());
    }

    @Test
    void toListeningSessionList_withValidAttributes_convertsListeningSessionList(){
        ListeningSession listeningSession = new ListeningSession();
        listeningSession.setEmail("validEmail@email.com");
        listeningSession.setStartSession(LocalDateTime.of(2024, 6, 4, 12, 30, 30));
        listeningSession.setEndSession(LocalDateTime.of(2024, 6, 4, 15, 59, 45));
        listeningSession.setListeningType("Spotify");
        listeningSession.setNotes("");

        List<ListeningSession> listeningSessionList = new ArrayList<>();

        listeningSessionList.add(listeningSession);

        List<ListeningSessionModel> listeningSessionModels = modelConverter.toListeningSessionModelList(listeningSessionList);

        assertEquals(listeningSessionList.size(), listeningSessionModels.size());
        assertEquals(listeningSessionList.get(0).getEmail(), listeningSessionModels.get(0).getEmail());
        assertEquals(listeningSessionList.get(0).getStartSession(), listeningSessionModels.get(0).getStartSession());
        assertEquals(listeningSessionList.get(0).getListeningType(), listeningSessionModels.get(0).getListeningType());
    }
}
