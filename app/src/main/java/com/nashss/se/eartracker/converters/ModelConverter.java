package com.nashss.se.eartracker.converters;

import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.dynamodb.models.ListeningType;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import com.nashss.se.eartracker.models.ListeningTypeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {
    /**
     * Converts a provided {@link ListeningSession} into a {@link ListeningSessionModel} representation.
     *
     * @param listeningSession the listeningSession to convert
     * @return the converted listeningSession
     */
    public ListeningSessionModel toListeningSessionModel(ListeningSession listeningSession) {
        return ListeningSessionModel.builder()
                .withEmail(listeningSession.getEmail())
                .withStartSession(listeningSession.getStartSession())
                .withEndSession(listeningSession.getEndSession())
                .withListeningType(listeningSession.getListeningType())
                .withTimeElapsed(listeningSession.getTimeElapsed())
                .withNotes(listeningSession.getNotes())
                .build();
    }

    /**
     * Converts a provided {@link ListeningType} into a {@link ListeningTypeModel} representation.
     *
     * @param listeningType the listeningType to convert
     * @return the converted listeningSession
     */
    public ListeningTypeModel toListeningTypeModel(ListeningType listeningType) {
        return ListeningTypeModel.builder()
                .withEmail(listeningType.getEmail())
                .withListeningType(listeningType.getListeningType())
                .withNotes(listeningType.getNotes())
                .build();
    }

    /**
     * Converts a list of ListeningSessions to a list of ListeningSessionModels.
     *
     * @param listeningSessions The ListeningSessions to convert to ListeningSessionModels
     * @return The converted list of ListeningSessionModels
     */
    public List<ListeningSessionModel> toListeningSessionModelList(List<ListeningSession> listeningSessions) {
        List<ListeningSessionModel> listeningSessionModels = new ArrayList<>();

        for (ListeningSession listeningSession : listeningSessions) {
            listeningSessionModels.add(toListeningSessionModel(listeningSession));
        }
        return listeningSessionModels;
    }

}
