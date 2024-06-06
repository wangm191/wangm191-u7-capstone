package com.nashss.se.eartracker.converters;

import com.nashss.se.eartracker.dynamodb.models.ListeningSession;
import com.nashss.se.eartracker.models.ListeningSessionModel;

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
}
