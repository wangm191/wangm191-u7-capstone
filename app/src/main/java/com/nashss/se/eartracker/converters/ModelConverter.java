package com.nashss.se.eartracker.converters;

import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.dynamodb.models.ListeningType;
import com.nashss.se.eartracker.models.ListeningSessionModel;
import com.nashss.se.eartracker.models.ListeningTypeModel;

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
                .withNotes(listeningSession.getNotes())
                .build();
    }

    /**
     * Converts a provided {@link ListeningType} into a {@link ListeningTypeModel} representation.
     *
     * @param listeningType the listeningType to convert
     * @return the converted listeningSession
     */
    public ListeningTypeModel toListeningSessionModel(ListeningType listeningType) {
        return ListeningTypeModel.builder()
                .withEmail(listeningType.getEmail())
                .withListeningType(listeningType.getListeningType())
                .withNotes(listeningType.getNotes())
                .build();
    }
}
